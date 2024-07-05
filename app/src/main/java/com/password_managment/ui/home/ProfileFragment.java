package com.password_managment.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;
import com.password_managment.databinding.FragmentProfileBinding;
import com.password_managment.utils.helpers.SharedPreferencesHelper;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FormFieldComponent emailField;
    private FormFieldComponent nameField;
    private HomeViewModel viewModel;
    private SharedPreferencesHelper preferencesHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        preferencesHelper = new SharedPreferencesHelper(requireActivity());


        emailField = binding.formField;
         nameField = binding.formField2;

        setupFields();
        setupObservers();
        setupListeners();

        return view;
    }

    public void setupFields() {
        nameField.setLabel("Name");
        nameField.setType(InputType.TYPE_CLASS_TEXT);
        nameField.setActive(false, requireActivity());

        emailField.setLabel("Email");
        emailField.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailField.setActive(false, requireActivity());
        binding.buttonEdit.setButton("Edit");
        binding.buttonDelete.setButton("Delete");
    }

    public void setupObservers() {
        viewModel.user.observe(requireActivity(), user -> {
            nameField.setInputText(user.getName());
            emailField.setInputText(user.getEmail());
        });
    }

    public void setupListeners() {
        binding.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameField.setActive(true, requireActivity());
            }
        });
    }
}