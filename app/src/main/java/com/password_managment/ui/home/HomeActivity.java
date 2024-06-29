package com.password_managment.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.content.res.ColorStateList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.password_managment.R;
import com.password_managment.databinding.ActivityHomeBinding;
import com.password_managment.utils.helpers.FragmentHelper;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FragmentHelper fragmentHelper;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), this);
        fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());

        setupBottomNavigationView();
    }

    private void setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            animateBottomNavigationViewColor(item.getItemId());

                if(item.getItemId() == R.id.home)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
                if(item.getItemId() == R.id.add)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new AddFragment());
                if(item.getItemId() == R.id.profile)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new ProfileFragment());

                return  true;
        });
    }

    private void animateBottomNavigationViewColor(int itemId) {
        int colorFrom = getResources().getColor(R.color.white);
        int colorTo = getResources().getColor(R.color.gray);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(300);
        colorAnimation.addUpdateListener(animator -> {
            int animatedValue = (int) animator.getAnimatedValue();
            ColorStateList animatedColor = ColorStateList.valueOf(animatedValue);
            binding.bottomNavigationView.setItemIconTintList(animatedColor);
            binding.bottomNavigationView.setItemTextColor(animatedColor);
        });
        colorAnimation.start();
    }

    public void showHomeFragment() {
        fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
    }
}
