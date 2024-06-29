package com.password_managment.ui.launcher;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.R;
import com.password_managment.ui.auth.AuthActivity;
import com.password_managment.repository.AuthRepository;
import com.password_managment.utils.helpers.ActivityHelper;
import com.password_managment.ui.home.HomeActivity;

public class LauncherActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ActivityHelper activityHelper;
    private AuthRepository authRepository;


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
        authRepository = AuthRepository.getInstance();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if(authRepository.getCurrentUser() == null) {
            activityHelper.startNewActivity(AuthActivity.class);
        } else {
            activityHelper.startNewActivity(HomeActivity.class);
        }
    }
}