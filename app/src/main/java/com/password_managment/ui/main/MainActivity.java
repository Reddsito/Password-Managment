package com.password_managment.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.password_managment.R;
import com.password_managment.repository.AuthRepository;
import com.password_managment.ui.home.HomeViewModel;
import com.password_managment.ui.launcher.LauncherActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    HomeViewModel viewModel;
    AuthRepository authRepository;

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

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        observeLoadingState();

        // Verificar si hay un usuario autenticado
        if (authRepository.getCurrentUser() != null) {
            String userId = authRepository.getCurrentUser().getUid();
            viewModel.setUserId(userId); // Configurar el ID del usuario en el ViewModel
            viewModel.fetchUser(); // Iniciar la solicitud para obtener el usuario
        } else {
            startLauncherActivity(); // Si no hay usuario autenticado, iniciar LauncherActivity
        }
    }

    private void observeLoadingState() {
        viewModel.loading.observe(this, isLoading -> {
            if (!isLoading) {
                startLauncherActivity();
            }
        });
    }

    private void startLauncherActivity() {
        Intent intent = new Intent(MainActivity.this, LauncherActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }
}