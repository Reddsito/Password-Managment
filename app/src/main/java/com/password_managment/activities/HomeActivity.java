package com.password_managment.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.password_managment.R;
import com.password_managment.auth.AuthManager;
import com.password_managment.databinding.ActivityHomeBinding;
import com.password_managment.fragments.AddFragment;
import com.password_managment.fragments.HomeFragment;
import com.password_managment.fragments.ProfileFragment;
import com.password_managment.helpers.FragmentHelper;
import com.password_managment.models.User;
import com.password_managment.repository.UserRepository;

import java.util.concurrent.CompletableFuture;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    FragmentHelper fragmentHelper;
    BottomNavigationView bottomNavigationView;
    AuthManager authManager;
    UserRepository userRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
        bottomNavigationView = binding.bottomNavigationView;
        authManager = AuthManager.getInstance();
        userRepository = new UserRepository();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            animateBottomNavigationViewColor(item.getItemId());
            return true;
        });



        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if ( item.getItemId() == R.id.home) {
                fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
            }

            else if ( item.getItemId() == R.id.add) {
                fragmentHelper.replaceFragment(R.id.frame_layout, new AddFragment());
            }

           else if ( item.getItemId() == R.id.profile) {
                HomeFragment homeFragment = new HomeFragment();
                fragmentHelper.replaceFragment(R.id.frame_layout, new ProfileFragment());
            }
            return true;
        });


    }

    public CompletableFuture<User> getUser(String userId) {
        return userRepository.getUser(userId);
    }

    public FirebaseUser getFirebaseUser() {
      return authManager.getCurrentUser();
    }

    private void animateBottomNavigationViewColor(int itemId) {
        int colorFrom = getResources().getColor(R.color.white);
        int colorTo = getResources().getColor(R.color.gray);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(300);
        colorAnimation.addUpdateListener(animator -> {
            int animatedValue = (int) animator.getAnimatedValue();
            ColorStateList animatedColor = ColorStateList.valueOf(animatedValue);
            bottomNavigationView.setItemIconTintList(animatedColor);
            bottomNavigationView.setItemTextColor(animatedColor);
        });
        colorAnimation.start();
    }

}