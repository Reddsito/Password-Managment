package com.password_managment.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.password_managment.R;
import com.password_managment.databinding.FragmentAddBinding;
import com.password_managment.databinding.FragmentAddGroupBinding;
import com.password_managment.models.PasswordGroup;
import com.password_managment.utils.helpers.SharedPreferencesHelper;
import com.password_managment.utils.helpers.ToastHelper;

import java.util.ArrayList;

public class AddGroupFragment extends Fragment {
    private FragmentAddGroupBinding binding;
    private String title;
    private String password;
    HomeViewModel viewModel;
    ToastHelper toastHelper;
    SharedPreferencesHelper preferencesHelper;
    ArrayAdapter<PasswordGroup> adapter;
    AutoCompleteTextView autoCompleteTextView;
    String passwordId;
    String passwordName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddGroupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        toastHelper = new ToastHelper();
        preferencesHelper = new SharedPreferencesHelper(requireActivity());
        String userId = preferencesHelper.getString("user_id", "");
        autoCompleteTextView = binding.autoComplete;

        Bundle extras = getArguments();
        if (extras != null) {
            title = extras.getString("title");
            password = extras.getString("password");
        }

        adapter = new ArrayAdapter<PasswordGroup>(requireActivity(), android.R.layout.simple_list_item_1 );
        autoCompleteTextView.setAdapter(adapter);

        setupSubscribers();
        setupListeners();
        viewModel.fetchPasswordGroup(userId);

        return view;
    }

    private void setupSubscribers() {
        viewModel.passwordGroups.observe(getViewLifecycleOwner(), groups -> {
            if(groups.isEmpty()) {
                binding.textLayout.setVisibility(View.GONE);
                binding.textInfo.setVisibility(View.VISIBLE);
                return;
            }
            adapter.clear();
            adapter.addAll(groups);
            binding.textLayout.setVisibility(View.VISIBLE);
            binding.textInfo.setVisibility(View.GONE);

        });
    }

    private void setupListeners() {
        binding.createPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.createPassword(title, password, passwordId, passwordName);
            }
        });

       autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               PasswordGroup passwordGroup = (PasswordGroup) parent.getItemAtPosition(position);
               passwordId = passwordGroup.getId();
               passwordName = passwordGroup.getName();
           }
       });

    }
}