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
import com.password_managment.models.Password;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.utils.helpers.FragmentHelper;

public class CreatePasswordFragment extends Fragment {

    private FormFieldComponent title;
    private FormFieldComponent password;
    private Button createButton;
    private HomeViewModel homeViewModel;
    private PasswordRepository passwordRepository;
    private FragmentHelper fragmentHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_password, container, false);

        fragmentHelper = new FragmentHelper(getChildFragmentManager(), getActivity());
        passwordRepository = new PasswordRepository();
        title = view.findViewById(R.id.form_field);
        password = view.findViewById(R.id.form_field2);
        createButton = view.findViewById(R.id.button_create);

        title.setLabel("Title");
        title.setHint("Title");
        title.setType(InputType.TYPE_CLASS_TEXT);

        password.setLabel("Password");
        password.setHint("*********");
        password.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        createButton.setOnClickListener(v -> {
            String titleText = title.getText();
            String passwordText = password.getText();

            if (TextUtils.isEmpty(titleText) || TextUtils.isEmpty(passwordText)) {
                Toast.makeText(requireContext(), "Title or password can't be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            homeViewModel.userId.observe(getViewLifecycleOwner(), userId -> {
                if (userId != null) {
                    passwordRepository.createPassword(userId, new Password(titleText, passwordText))
                            .thenAccept(response -> {
                                showHomeFragment();
                            }).exceptionally(e -> {
                                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                return null;
                            });
                }
            });
        });

        return view;
    }

    private void showHomeFragment() {
        if (getActivity() != null) {
            ((HomeActivity) getActivity()).showHomeFragment();
        }
    }
}
