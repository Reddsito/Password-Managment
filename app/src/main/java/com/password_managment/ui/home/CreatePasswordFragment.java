package com.password_managment.ui.home;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;
import com.password_managment.databinding.FragmentCreatePasswordBinding;
import com.password_managment.models.Password;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.utils.helpers.FragmentHelper;
import com.password_managment.utils.helpers.ToastHelper;

public class CreatePasswordFragment extends Fragment {

    private FormFieldComponent title;
    private FormFieldComponent password;
    private Button createButton;
    private PasswordRepository passwordRepository;
    private FragmentHelper fragmentHelper;
    private HomeViewModel viewModel;
    private FragmentCreatePasswordBinding binding;
    private ToastHelper toastHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreatePasswordBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        fragmentHelper = new FragmentHelper(getChildFragmentManager(), getActivity());
        passwordRepository = new PasswordRepository();
        toastHelper = new ToastHelper();

        title = binding.formField;
        password = binding.formField2;
        createButton = binding.buttonCreate;

        title.setLabel("Title");
        title.setHint("Title");
        title.setType(InputType.TYPE_CLASS_TEXT);

        password.setLabel("Password");
        password.setHint("*********");
        password.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        setupObservers();
        setupListeners();

        return view;
    }

    private void setupListeners() {
        createButton.setOnClickListener(v -> {
            String titleText = title.getText();
            String passwordText = password.getText();
            viewModel.showAddGroupFragment(titleText, passwordText);
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


    private void showToast(String message) {
        toastHelper.showShortToast(requireActivity(), message);
    }
}
