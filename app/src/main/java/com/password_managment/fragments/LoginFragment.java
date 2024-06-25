package com.password_managment.fragments;

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
import com.password_managment.activities.AuthActivity;
import com.password_managment.components.FormFieldComponent;

public class LoginFragment extends Fragment {

    private TextView textViewRegister;
    private FormFieldComponent passwordField;
    private FormFieldComponent emailField;
    private Button loginButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        TextView textViewRegister = view.findViewById(R.id.text_view_register);
        emailField = view.findViewById(R.id.form_field);
        passwordField = view.findViewById(R.id.form_field2);
        loginButton = view.findViewById(R.id.button_login);


        emailField.setLabel("Email");
        emailField.setHint("example@gmail.com");
        emailField.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        passwordField.setLabel("Password");
        passwordField.setHint("*********");
        passwordField.setType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        String text = textViewRegister.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textViewRegister.setText(spannableString);

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    ((AuthActivity) getActivity()).showRegisterFragment();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    String email = emailField.getText().trim().toLowerCase();
                    String password = passwordField.getText().trim();

                    if( TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ) {
                        Toast.makeText(requireContext(), "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ((AuthActivity) getActivity()).signIn(email, password);
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