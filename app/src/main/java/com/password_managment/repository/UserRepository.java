package com.password_managment.repository;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.models.Password;
import com.password_managment.models.PasswordGroup;
import com.password_managment.models.User;
import com.password_managment.models.UserPassword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
