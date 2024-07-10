package com.password_managment.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.password_managment.R;
import com.password_managment.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

    private FragmentAddBinding binding;
    HomeViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        View view = binding.getRoot();

        binding.createGroup.setButton("Create group");
        binding.createPassword.setButton("Create password");

        setupListeners();

        return view;
    }

    public void setupListeners() {
        binding.createPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AddFragment", "Addfragment");
                viewModel.showCreatePasswordFragment();
            }
        });

        binding.createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.showCreateGroupFragment();
            }
        });

    }

}