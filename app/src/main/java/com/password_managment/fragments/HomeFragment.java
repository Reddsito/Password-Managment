package com.password_managment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.password_managment.R;
import com.password_managment.activities.AuthActivity;
import com.password_managment.activities.HomeActivity;
import com.password_managment.models.User;
import com.password_managment.utils.Functions;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HomeFragment extends Fragment {

    TextView textViewName;
    FirebaseUser firebaseUser;
    Functions functions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textViewName = view.findViewById(R.id.tv_name);
        functions = new Functions();

        if(getActivity() != null ) {
            firebaseUser = ((HomeActivity) getActivity()).getFirebaseUser();
        }

        getUser(firebaseUser.getUid()).thenAccept(user -> {
            requireActivity().runOnUiThread(() -> {
                System.out.println(user.getName());
                String name = functions.capitalize(user.getName());
                textViewName.setText("Hello, "+ name);
            });
        }).exceptionally(e -> {
            requireActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
            return null;
        });
        return view;
    }

    public CompletableFuture<User> getUser(String userId) {
        if (getActivity() != null) {
            return ((HomeActivity) getActivity()).getUser(userId);
        }
        return null;
    }
}