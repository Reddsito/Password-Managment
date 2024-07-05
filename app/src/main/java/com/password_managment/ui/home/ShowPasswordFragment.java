package com.password_managment.ui.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;
import com.password_managment.databinding.FragmentHomeBinding;
import com.password_managment.databinding.FragmentShowPasswordBinding;
import com.password_managment.models.Password;
import com.password_managment.utils.helpers.DialogHelper;
import com.password_managment.utils.helpers.ToastHelper;

public class ShowPasswordFragment extends Fragment {

    private FragmentShowPasswordBinding binding;
    private HomeViewModel viewModel;
    private Password password;
    private FormFieldComponent field;
    private Boolean isActive = false;
    private Boolean editActive = false;
    private DialogHelper dialogHelper;
    private ToastHelper toastHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        dialogHelper = new DialogHelper(requireActivity());
        toastHelper = new ToastHelper();

        Bundle extras = getArguments();
        if (extras != null) {
            String title = extras.getString("title");
            String pass = extras.getString("password");
            System.out.println(title + pass);
            password = new Password();
            password.setTitle(title);
            password.setPassword(pass);
        }
        setupFields();

        binding.buttonEdit.setButton("Edit");
        binding.buttonDelete.setButton("Delete");

        setupObservers();
        setupListeners();
        return  view;
    }

    private void setupObservers() {

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

        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isActive) return;
                editActive = !editActive;
                field.setActive(editActive, requireActivity());
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