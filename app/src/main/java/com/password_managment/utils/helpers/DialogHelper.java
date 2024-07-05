package com.password_managment.utils.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.password_managment.R;

import java.util.Random;

public class DialogHelper {
    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public DialogHelper(Context context) {
        this.context = context;
        this.sharedPreferencesHelper = new SharedPreferencesHelper(context);
    }

    public void showSecurityQuestionDialog(Runnable onSuccess, Runnable onFailure) {
        // Inflar el layout del diálogo
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(com.password_managment.R.layout.dialog_security_question, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);

        // Obtener las vistas del layout del diálogo
        EditText answerEditText = dialogView.findViewById(R.id.answerEditText);
        Button submitButton = dialogView.findViewById(R.id.submitButton);

        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        String question = "";

        switch (randomNumber) {
            case 1:
                question = "Nombre de tu mejor amigo";
                break;
            case 2:
                question = "Provincia donde naciste";
                break;
            case 3:
                question = "Nombre de tu mamá";
                break;
        }

        String response = "question" + Integer.toString(randomNumber);
        String correctAnswer = sharedPreferencesHelper.getString(response,"");
        builder.setTitle(question);

        AlertDialog dialog = builder.create();

        // Configurar el botón de enviar
        submitButton.setOnClickListener(v -> {
            String userAnswer = answerEditText.getText().toString().trim().toLowerCase();

            if (userAnswer.equals(correctAnswer.toLowerCase())) {
                onSuccess.run();
                dialog.dismiss();
            } else {
                onFailure.run();
                Toast.makeText(context, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
