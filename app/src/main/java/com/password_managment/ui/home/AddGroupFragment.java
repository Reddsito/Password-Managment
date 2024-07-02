package com.password_managment.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.password_managment.R;
import com.password_managment.databinding.FragmentAddBinding;
import com.password_managment.databinding.FragmentAddGroupBinding;
import com.password_managment.utils.helpers.ToastHelper;

public class AddGroupFragment extends Fragment {
    private FragmentAddGroupBinding binding;
    private String title;
    private String password;
    HomeViewModel viewModel;
    ArrayAdapter<String> adapterItems;
    ToastHelper toastHelper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        toastHelper = new ToastHelper();
        adapterItems = new ArrayAdapter<String>(requireActivity(), R.layout.dropdown_item);
        binding.autoCompleteText.setAdapter(adapterItems);

        Bundle extras = getArguments();
        if (extras != null) {
            title = extras.getString("title");
            password = extras.getString("password");
        }

        setupSubscribers();
        setupListeners();
        viewModel.fetchGroups();

        return view;
    }

    private void setupSubscribers() {
        viewModel.groups.observe(getViewLifecycleOwner(), groups -> {
            if(groups.isEmpty()) {
                binding.textInfo.setVisibility(View.GONE);
                binding.textInfo.setVisibility(View.VISIBLE);
                return;
            }
            binding.textInfo.setVisibility(View.GONE);
            adapterItems.addAll(groups);
            binding.textLayout.setVisibility(View.VISIBLE);
        });
    }

    private void setupListeners() {
        binding.autoCompleteText.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.createPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.createPassword(title, password);
            }
        });

    }
}