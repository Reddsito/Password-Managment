package com.password_managment.repository;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;

public class SecurityResponsesRepository {
    private final FirebaseFirestore db;

    public SecurityResponsesRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void saveSecurityQuestion(String userId, String questionId, Map<String, Object> questionData, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .collection("security_questions")
                .document(questionId)
                .set(questionData)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> {
                    callback.onFailure("Error al guardar pregunta de seguridad: " + e.getMessage());
                });
    }

    public void getSecurityQuestions(String userId, OnSuccessListener<QuerySnapshot> successListener, OnFailureListener failureListener) {
        db.collection("users")
                .document(userId)
                .collection("security_questions")
                .get()
                .addOnSuccessListener(successListener)
                .addOnFailureListener(failureListener);
    }

    public interface FirestoreCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
