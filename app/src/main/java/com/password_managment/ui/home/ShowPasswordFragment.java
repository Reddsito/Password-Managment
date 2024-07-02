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

public class ShowPasswordFragment extends Fragment {

    private FragmentShowPasswordBinding binding;
    private HomeViewModel viewModel;
    private Password password;
    private FormFieldComponent field;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowPasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);


        Bundle extras = getArguments();
        if (extras != null) {
            String title = extras.getString("title");
            String pass = extras.getString("password");
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
    }

    public void setupFields() {
        field = binding.formField;
        field.setLabel(password.getTitle());
        field.setInputText(password.getPassword());
        field.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        field.setIcon(R.drawable.eye_off, requireActivity(), 48, 48);
        field.setOnIconClickListener(new FormFieldComponent.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ejecutar");
            }
        });
    }
}