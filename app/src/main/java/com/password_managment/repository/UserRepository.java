package com.password_managment.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.models.User;

import java.util.concurrent.CompletableFuture;

public class UserRepository {
    private final FirebaseFirestore db;

    public UserRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public CompletableFuture<User> getUser(String userId) {
        CompletableFuture<User> future = new CompletableFuture<>();
        System.out.println(userId);

        db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        future.complete(user);
                    } else {
                        future.completeExceptionally(new Exception("User not found"));
                    }
                })
                .addOnFailureListener(future::completeExceptionally);

        return future;
    }

}
