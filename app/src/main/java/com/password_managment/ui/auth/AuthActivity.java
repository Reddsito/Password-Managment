package com.password_managment.ui.auth;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.password_managment.R;
import com.password_managment.repository.AuthRepository;
import com.password_managment.utils.helpers.ActivityHelper;
import com.password_managment.utils.helpers.FragmentHelper;
import com.password_managment.repository.SecurityResponsesRepository;
import com.password_managment.ui.home.HomeActivity;

import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private FragmentHelper fragmentHelper;
    private AuthRepository authRepository;
    private ActivityHelper activityHelper;
    private SecurityResponsesRepository securityResponsesRepository;


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
        authRepository = AuthRepository.getInstance();
        activityHelper = new ActivityHelper(this);
        securityResponsesRepository = new SecurityResponsesRepository();

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

    public void showSecurityQuestionFragment() {
        SecurityQuestionsFragment securityQuestionsFragment = new SecurityQuestionsFragment();
        fragmentHelper.replaceFragment(R.id.fragment_container, securityQuestionsFragment);
    }

    public void signIn(String email, String password) {
        authRepository.signInWithEmailAndPassword(email, password, this, new AuthRepository.AuthCallback() {
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
        authRepository.createUserWithEmailAndPassword(email, password, name, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                showSecurityQuestionFragment();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Error en el inicio de sesión, mostrar Toast
                Toast.makeText(AuthActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveSecurityQuestions(Map<String, Object> securityQuestions) {
        String userId = authRepository.getCurrentUser().getUid();
            securityResponsesRepository.saveSecurityQuestion(userId, "questions", securityQuestions, new SecurityResponsesRepository.FirestoreCallback() {
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

}