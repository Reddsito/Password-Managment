package com.password_managment.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.R;
import com.password_managment.auth.AuthManager;
import com.password_managment.helpers.ActivityHelper;

public class LauncherActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ActivityHelper activityHelper;
    private AuthManager authManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launcher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        activityHelper = new ActivityHelper(this);
        authManager = AuthManager.getInstance();

        authManager.signOut();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if(authManager.getCurrentUser() == null) {
            activityHelper.startNewActivity(AuthActivity.class);
        } else {
            activityHelper.startNewActivity(HomeActivity.class);
        }
    }
}