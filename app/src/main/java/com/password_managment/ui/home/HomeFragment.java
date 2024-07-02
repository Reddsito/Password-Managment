package com.password_managment.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.password_managment.R;
import com.password_managment.components.adapters.PasswordListAdapter;
import com.password_managment.databinding.FragmentHomeBinding;

import com.password_managment.models.Password;
import com.password_managment.utils.Functions;
import com.password_managment.utils.helpers.FragmentHelper;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements  PasswordListAdapter.OnPasswordClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private final Functions functions = new Functions();
    private FragmentHelper fragmentHelper;
    RecyclerView passwordList;
    PasswordListAdapter passwordAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        fragmentHelper = new FragmentHelper(getChildFragmentManager(), getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        setupObservers();
        setupListeners();

        return view;
    }

    private void setupObservers() {
        viewModel.user.observe(getViewLifecycleOwner(), user -> {
            binding.tvName.setText("Hello, " + functions.capitalize(user.getName()));
            binding.tvName.setVisibility(View.VISIBLE);
        });

        viewModel.passwords.observe(getViewLifecycleOwner(), passwords -> {
            if (passwords.isEmpty()) {
                binding.buttonCreatePassword.setVisibility(View.VISIBLE);
            } else {
                passwordAdapter = new PasswordListAdapter(passwords, requireActivity(), this);
                binding.buttonCreatePassword.setVisibility(View.GONE);
                binding.passwordList.setAdapter(passwordAdapter);
                binding.passwordList.setLayoutManager(new LinearLayoutManager(requireActivity()));
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                binding.buttonCreatePassword.setVisibility(View.GONE);
                binding.tvName.setVisibility(View.GONE);
            }
        });
    }

    private void setupListeners() {
        binding.buttonCreatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreatePasswordFragment();
            }
        });
    }

    private void showCreatePasswordFragment() {
        if(getActivity() == null ) return;
        ((HomeActivity) getActivity()).showCreatePasswordFragment();
    }

    @Override
    public void onPasswordClick(Password password) {
        viewModel.showEditPasswordFragment(password);
    }
}
