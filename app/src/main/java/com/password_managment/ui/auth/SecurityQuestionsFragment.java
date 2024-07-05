package com.password_managment.ui.auth;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.password_managment.R;
import com.password_managment.components.FormFieldComponent;

import java.util.HashMap;
import java.util.Map;

public class SecurityQuestionsFragment extends Fragment {
    private FormFieldComponent question1;
    private FormFieldComponent question2;
    private FormFieldComponent question3;
    private Button sendKeysButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_security_questions, container, false);

        question1 = view.findViewById(R.id.form_field);
        question2 = view.findViewById(R.id.form_field2);
        question3 = view.findViewById(R.id.form_field3);
        sendKeysButton = view.findViewById(R.id.button_sendKeys);


        question1.setLabel("Nombre de tu mejor amigo");
        question1.setHint("Respuesta");
        question1.setType(InputType.TYPE_CLASS_TEXT);

        question2.setLabel("Provincia donde naciste");
        question2.setHint("Respuesta");
        question2.setType(InputType.TYPE_CLASS_TEXT);

        question3.setLabel("Nombre de tu mamá");
        question3.setHint("Respuesta");
        question3.setType(InputType.TYPE_CLASS_TEXT);

        sendKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null ) {
                    String response1 = question1.getText().trim().toLowerCase();
                    String response2 = question2.getText().trim().toLowerCase();
                    String response3 = question3.getText().trim().toLowerCase();

                    if (TextUtils.isEmpty(response1) || TextUtils.isEmpty(response2) || TextUtils.isEmpty(response3)) {
                        Toast.makeText(requireContext(), "No se permiten campos vacíos", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Map<String, Object> securityQuestions = new HashMap<>();
                    securityQuestions.put("question1", response1);
                    securityQuestions.put("question2", response2);
                    securityQuestions.put("question3", response3);

                    ((AuthActivity) getActivity()).saveSecurityQuestions(securityQuestions);
                }

            }
        });

        return view;
    }


}