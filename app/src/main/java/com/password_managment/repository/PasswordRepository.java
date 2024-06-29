package com.password_managment.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.password_managment.models.Password;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PasswordRepository {
    private static final String COLLECTION_USERS = "users";
    private static final String COLLECTION_PASSWORDS = "passwords";
    private FirebaseFirestore db;

    public PasswordRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Void> createPassword(String userId, Password password) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        db.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_PASSWORDS)
                .add(password)
                .addOnSuccessListener(documentReference -> future.complete(null))
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }

    public CompletableFuture<List<Password>> getPasswords(String userId) {
        CompletableFuture<List<Password>> future = new CompletableFuture<>();
        db.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_PASSWORDS)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Password> passwords = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Password password = doc.toObject(Password.class);
                        passwords.add(password);
                    }
                    future.complete(passwords);
                })
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }

    public CompletableFuture<Void> deletePassword(String userId, String passwordId) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        db.collection(COLLECTION_USERS)
                .document(userId)
                .collection(COLLECTION_PASSWORDS)
                .document(passwordId)
                .delete()
                .addOnSuccessListener(aVoid -> future.complete(null))
                .addOnFailureListener(future::completeExceptionally);
        return future;
    }
}
