package com.password_managment.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.R;
import com.password_managment.repository.AuthRepository;
import com.password_managment.ui.home.HomeViewModel;
import com.password_managment.ui.launcher.LauncherActivity;
import com.password_managment.utils.helpers.ActivityHelper;
import com.password_managment.utils.helpers.SharedPreferencesHelper;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    AuthRepository authRepository;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();
        authRepository = AuthRepository.getInstance();
        preferencesHelper = new SharedPreferencesHelper(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

       FirebaseUser user = authRepository.getCurrentUser();
        if (user != null) {
            preferencesHelper.saveString("user_id", user.getUid());
        }
        startLauncherActivity();
    }

    private void startLauncherActivity() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }, 1500);
    }
}