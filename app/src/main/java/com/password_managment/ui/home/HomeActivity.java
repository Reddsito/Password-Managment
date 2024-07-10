package com.password_managment.ui.home;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.content.res.ColorStateList;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.password_managment.R;
import com.password_managment.databinding.ActivityHomeBinding;
import com.password_managment.utils.helpers.FragmentHelper;
import com.password_managment.utils.helpers.SharedPreferencesHelper;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private FragmentHelper fragmentHelper;
    private HomeViewModel viewModel;
    SharedPreferencesHelper preferencesHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        preferencesHelper = new SharedPreferencesHelper(this);
        fragmentHelper = new FragmentHelper(getSupportFragmentManager(), this);
        fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
        String userId = preferencesHelper.getString("user_id", "");

        setupSubscribers();
        viewModel.fetchUser(userId);
        viewModel.fetchPasswordGroup(userId);
        setupBottomNavigationView();

    }

    private void setupBottomNavigationView() {
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
                if(item.getItemId() == R.id.home)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
                if(item.getItemId() == R.id.add)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new AddFragment());
                if(item.getItemId() == R.id.profile)
                    fragmentHelper.replaceFragment(R.id.frame_layout, new ProfileFragment());

                return true;
        });
    }

    public void setupSubscribers() {
        viewModel.showHome.observe(this, navigate -> {
                if (navigate.getContentIfNotHandled()) showHomeFragment();
            });

        viewModel.showCreatePassword.observe(this, navigate -> {
            if(navigate.getContentIfNotHandled()) showCreatePasswordFragment();
        });

        viewModel.passwordData.observe(this, this::showAddGroupFragment);

        viewModel.showEditPassword.observe(this, this::showEditPasswordFragment);

        viewModel.showCreateGroup.observe(this, data -> {
            showCreateGroupFragment();
        });

    }

    public void showHomeFragment() {
        binding.bottomNavigationView.setSelectedItemId(R.id.home);

        fragmentHelper.replaceFragment(R.id.frame_layout, new HomeFragment());
    }

    public void showCreatePasswordFragment() {
        fragmentHelper.replaceFragment(R.id.frame_layout, new CreatePasswordFragment());
    }

    public void showAddGroupFragment(Bundle data) {
        fragmentHelper.replaceFragmentWithExtras(R.id.frame_layout, new AddGroupFragment(),data);
    }

    public void showEditPasswordFragment(Bundle data) {
        fragmentHelper.replaceFragmentWithExtras(R.id.frame_layout, new ShowPasswordFragment(), data);
    }

    public void showCreateGroupFragment() {
        fragmentHelper.replaceFragment(R.id.frame_layout, new CreateGroupFragment());
    }

}
