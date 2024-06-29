package com.password_managment.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;

public class RegisterFragment extends Fragment {

    private TextView textViewSignUp;
    private FormFieldComponent passwordField;
    private FormFieldComponent emailField;
    private Button registerButton;
    private FormFieldComponent nameField;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_register, container, false);

        TextView textViewSignUp = view.findViewById(R.id.text_view_signup);
        nameField = view.findViewById(R.id.form_field);
        emailField = view.findViewById(R.id.form_field2);
        passwordField = view.findViewById(R.id.form_field3);
        registerButton = view.findViewById(R.id.button_register);

        emailField.setLabel("Name");
        emailField.setHint("Jhon Doe");
        emailField.setType(InputType.TYPE_CLASS_TEXT);

        emailField.setLabel("Email");
        emailField.setHint("example@gmail.com");
        emailField.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        passwordField.setLabel("Password");
        passwordField.setHint("*********");
        passwordField.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        String text = textViewSignUp.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewSignUp.setText(spannableString);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((AuthActivity) getActivity()).showLoginFragment();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null ) {
                    String email = emailField.getText().trim().toLowerCase();
                    String password = passwordField.getText().trim();
                    String name = nameField.getText().trim().toLowerCase();

                    if( TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) ) {
                        Toast.makeText(requireContext(), "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ((AuthActivity) getActivity()).signUp(email, name, password);
                }
            }
        });

        return view;
    }

    public SpannableString createSpannableString(String text) {
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}