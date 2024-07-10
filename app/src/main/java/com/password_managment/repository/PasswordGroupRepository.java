package com.password_managment.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.models.PasswordGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PasswordGroupRepository {

    private FirebaseFirestore db;

    public PasswordGroupRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<Void> createPasswordGroup(PasswordGroup passwordGroup, String userId) {
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();

        db.collection("users")
                .document(userId)
                .collection("password_groups")
                .add(passwordGroup)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        completableFuture.complete(null);
                    } else {
                        completableFuture.completeExceptionally(task.getException());
                    }
                });

        return completableFuture;
    }

    public CompletableFuture<List<PasswordGroup>> getPasswordGroups(String userId) {
        CompletableFuture<List<PasswordGroup>> completableFuture = new CompletableFuture<>();

        db.collection("users")
                .document(userId)
                .collection("password_groups")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<PasswordGroup> passwordGroups = new ArrayList<>();
                        task.getResult().forEach(document -> {
                            PasswordGroup group = document.toObject(PasswordGroup.class);
                            group.setId(document.getId());
                            passwordGroups.add(group);
                        });
                        completableFuture.complete(passwordGroups);
                    } else {
                        completableFuture.completeExceptionally(task.getException());
                    }
                });

        return completableFuture;
    }
}
