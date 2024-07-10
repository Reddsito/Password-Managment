package com.password_managment.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;
import com.password_managment.databinding.FragmentCreateGroupBinding;
import com.password_managment.databinding.FragmentProfileBinding;
import com.password_managment.models.PasswordGroup;
import com.password_managment.utils.helpers.SharedPreferencesHelper;
import com.password_managment.utils.helpers.ToastHelper;

public class CreateGroupFragment extends Fragment {
    FragmentCreateGroupBinding binding;
    FormFieldComponent field;
    HomeViewModel viewModel;
    SharedPreferencesHelper sharedPreferencesHelper;
    ToastHelper toastHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        sharedPreferencesHelper = new SharedPreferencesHelper(requireActivity());
        toastHelper = new ToastHelper();

        field = binding.formField;
        field.setLabel("Group name");
        field.setType(InputType.TYPE_CLASS_TEXT);
        field.setHint("Group example...");

        setupListeners();
        setupObservers();

        return view;

    }

    public void setupListeners() {
        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = sharedPreferencesHelper.getString("user_id", "");
                Log.d("Create group", userId);
                String name = field.getText();
                PasswordGroup passwordGroup = new PasswordGroup();
                passwordGroup.setName(name);
                viewModel.createPasswordGroup(userId, passwordGroup);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showHomeFragment();
            }
        });
    }

    public void setupObservers() {
        viewModel.toastMessage.observe(getViewLifecycleOwner(), message -> {
            toastHelper.showShortToast(requireActivity(), message);
        });
    }

}