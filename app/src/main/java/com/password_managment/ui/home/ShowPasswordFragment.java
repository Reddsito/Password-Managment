package com.password_managment.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;
import com.password_managment.databinding.FragmentHomeBinding;
import com.password_managment.databinding.FragmentShowPasswordBinding;
import com.password_managment.models.Password;
import com.password_managment.models.PasswordGroup;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.utils.helpers.DialogHelper;
import com.password_managment.utils.helpers.SharedPreferencesHelper;
import com.password_managment.utils.helpers.ToastHelper;

public class ShowPasswordFragment extends Fragment {

    private FragmentShowPasswordBinding binding;
    private HomeViewModel viewModel;
    Password password;
    private FormFieldComponent field;
    private Boolean isActive = false;
    private Boolean editActive = false;
    private DialogHelper dialogHelper;
    private ToastHelper toastHelper;
    ArrayAdapter<PasswordGroup> adapter;
    AutoCompleteTextView autoCompleteTextView;
    private PasswordRepository passwordRepository;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private PasswordGroup selectedGroup;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        dialogHelper = new DialogHelper(requireActivity());
        toastHelper = new ToastHelper();
        autoCompleteTextView = binding.autoComplete;
        passwordRepository = new PasswordRepository();
        sharedPreferencesHelper = new SharedPreferencesHelper(requireActivity());


        Bundle extras = getArguments();
        if (extras != null) {
            String passwordEncoded = extras.getString("editPassword");
            Gson gson = new Gson();
            password = gson.fromJson(passwordEncoded, Password.class);
        }
        setupFields();

        binding.buttonEdit.setButton("Edit");
        binding.buttonDelete.setButton("Delete");
        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1 );
        autoCompleteTextView.setAdapter(adapter);

        setupObservers();
        setupListeners();
        return view;
    }

    private void setupObservers() {
        viewModel.passwordGroups.observe(getViewLifecycleOwner(), groups -> {
            if(groups.isEmpty()) {
                binding.textLayout.setVisibility(View.GONE);
                PasswordGroup defaultGroup = new  PasswordGroup();
                defaultGroup.setName("Without group");
                defaultGroup.setId("");
                autoCompleteTextView.setText(defaultGroup.getName());
                adapter.add(defaultGroup);

                selectedGroup = defaultGroup;
                return;
            }
            adapter.clear();
            binding.textLayout.setVisibility(View.GONE);
            PasswordGroup defaultGroup = new  PasswordGroup();
            defaultGroup.setName("Without group");
            defaultGroup.setId("");
            adapter.add(defaultGroup);
            adapter.addAll(groups);

            for (PasswordGroup group : groups) {
                if (group.getId().equals(password.getGroupId())) {
                    autoCompleteTextView.setText(group.getName(), false);
                    break;
                }
            }

            selectedGroup = defaultGroup;

        });
    }

    private void setupListeners() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showHomeFragment();
            }
        });

        field.setOnIconClickListener(new FormFieldComponent.OnClickListener() {
            @Override
            public void onClick(View v) {
                isActive = !isActive;

                if(isActive) {
                    dialogHelper.showSecurityQuestionDialog(
                            () -> {
                                field.setIcon(R.drawable.eye, requireActivity(), 48, 48);
                                field.setType(InputType.TYPE_CLASS_TEXT );
                                },
                            () -> {
                                toastHelper.showShortToast(requireActivity(), "Incorrecto");
                            }
                    );
                } else {
                    field.setIcon(R.drawable.eye_off, requireActivity(), 48, 48);
                    field.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    field.setActive(false, requireActivity());
                }
            }


        });

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            selectedGroup = (PasswordGroup) parent.getItemAtPosition(position);
        });

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive) return;
                editActive = !editActive;
                field.setActive(editActive, requireActivity());
                binding.textLayout.setVisibility(View.VISIBLE);

                if(editActive) {
                    binding.buttonEdit.setButton("Save");
                } else {
                    binding.buttonEdit.setButton("Edit");
                    binding.textLayout.setVisibility(View.GONE);

                    String userId = sharedPreferencesHelper.getString("user_id", "");
                    if(selectedGroup != null && !selectedGroup.getId().equals(password.getId()) ) {
                        password.setGroupId(selectedGroup.getId());
                        password.setGroupName(selectedGroup.getName());
                        passwordRepository.updatePassword(userId, password.getId(), password );
                        viewModel.updatePassword(password);
                    }

                    if( !password.getPassword().equals(field.getText())) {
                        System.out.println(password.getPassword());
                        password.setPassword(field.getText().trim());
                        passwordRepository.updatePassword(userId, password.getId(), password );
                        viewModel.updatePassword(password);
                    }

                }

            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sharedPreferencesHelper.getString("user_id", "");
                viewModel.deletePassword(password, userId);
            }
        });

    }

    public void setupFields() {
        field = binding.formField;
        field.setLabel(password.getTitle());
        field.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        field.setIcon(R.drawable.eye_off, requireActivity(), 48, 48);
        field.setInputText(password.getPassword());
        field.setActive(false, requireActivity());
    }


}