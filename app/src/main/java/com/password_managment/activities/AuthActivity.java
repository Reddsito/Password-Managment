package com.password_managment.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.password_managment.R;
import com.password_managment.auth.AuthManager;
import com.password_managment.fragments.LoginFragment;
import com.password_managment.fragments.RegisterFragment;
import com.password_managment.helpers.ActivityHelper;
import com.password_managment.helpers.FragmentHelper;

public class AuthActivity extends AppCompatActivity {

    private FragmentHelper fragmentHelper;
    private AuthManager authManager;
    private ActivityHelper activityHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), this);
        authManager = AuthManager.getInstance();
        activityHelper = new ActivityHelper(this);

        if (savedInstanceState == null) {
            showLoginFragment();
        }


    }

    public void showLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        fragmentHelper.replaceFragment(R.id.fragment_container, loginFragment);
    }

    public void showRegisterFragment() {
        RegisterFragment registerFragment = new RegisterFragment();
        fragmentHelper.replaceFragment(R.id.fragment_container, registerFragment);
    }

    public void signIn(String email, String password) {
        authManager.signInWithEmailAndPassword(email, password, this, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess() {
                activityHelper.startNewActivity(HomeActivity.class);
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(AuthActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUp(String email, String name, String password) {
        authManager.createUserWithEmailAndPassword(email, password, name, new AuthManager.AuthCallback() {
            @Override
            public void onSuccess() {
                activityHelper.startNewActivity(HomeActivity.class);
            }

            @Override
            public void onFailure(String errorMessage) {
                // Error en el inicio de sesión, mostrar Toast
                Toast.makeText(AuthActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}