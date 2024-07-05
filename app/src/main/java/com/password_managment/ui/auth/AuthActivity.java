package com.password_managment.ui.auth;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.password_managment.R;
import com.password_managment.repository.AuthRepository;
import com.password_managment.utils.helpers.ActivityHelper;
import com.password_managment.utils.helpers.FragmentHelper;
import com.password_managment.repository.SecurityResponsesRepository;
import com.password_managment.ui.home.HomeActivity;
import com.password_managment.utils.helpers.SharedPreferencesHelper;

import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private FragmentHelper fragmentHelper;
    private AuthRepository authRepository;
    private ActivityHelper activityHelper;
    private SecurityResponsesRepository securityResponsesRepository;
    private SharedPreferencesHelper preferencesHelper;



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
        preferencesHelper = new SharedPreferencesHelper(this);
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
                FirebaseUser user = authRepository.getCurrentUser();
                securityResponsesRepository.getSecurityQuestions(user.getUid(), new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            for (Map.Entry<String, Object> entry : document.getData().entrySet()) {
                                String questionKey = entry.getKey();
                                String response = (String) entry.getValue();
                                System.out.println(response);
                                preferencesHelper.saveString(questionKey, response);
                            }
                        }
                    }
                }, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Auth_Activity", e.getMessage());
                    }
                });
                preferencesHelper.saveString("user_id", user.getUid());
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
                FirebaseUser user = authRepository.getCurrentUser();
                preferencesHelper.saveString("user_id", user.getUid());
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
                    for (Map.Entry<String, Object> entry : securityQuestions.entrySet()) {
                        String questionKey = entry.getKey();
                        String response = (String) entry.getValue();
                        preferencesHelper.saveString(questionKey, response);
                    }
                    activityHelper.startNewActivity(HomeActivity.class);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Toast.makeText(AuthActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
    }

}