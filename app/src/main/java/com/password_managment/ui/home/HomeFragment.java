package com.password_managment.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.password_managment.R;
import com.password_managment.components.adapters.PasswordListAdapter;
import com.password_managment.databinding.FragmentHomeBinding;

import com.password_managment.models.Password;
import com.password_managment.models.PasswordGroup;
import com.password_managment.utils.Functions;
import com.password_managment.utils.helpers.FragmentHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements  PasswordListAdapter.OnPasswordClickListener {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private final Functions functions = new Functions();
    private FragmentHelper fragmentHelper;
    RecyclerView passwordList;
    PasswordListAdapter passwordAdapter;
    Spinner groups;
    ArrayAdapter<PasswordGroup> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        groups = binding.spinner;


        fragmentHelper = new FragmentHelper(getChildFragmentManager(), getActivity());
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_list_item_1 );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        PasswordGroup defaultGroup = new PasswordGroup();
        defaultGroup.setName("All");
        adapter.add(defaultGroup);
        groups.setAdapter(adapter);


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

        viewModel.passwordGroups.observe(getViewLifecycleOwner(), passwordGroups -> {

            adapter.clear();
            PasswordGroup defaultGroup = new PasswordGroup();
            defaultGroup.setName("All");
            adapter.add(defaultGroup);
            adapter.addAll(passwordGroups);
            adapter.notifyDataSetChanged();
            binding.spinner.setVisibility(View.VISIBLE);
            binding.searchBar.setVisibility(View.VISIBLE);
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

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                passwordAdapter.getFilter().filter(newText);
                return false;
            }
        });

        groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PasswordGroup passwordGroup = (PasswordGroup) parent.getItemAtPosition(position);
                getSelectedGroup(passwordGroup);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void getSelectedGroup(PasswordGroup passwordSelected) {
        ArrayList<Password> passwordList = new ArrayList<>();

        if(Objects.equals(passwordSelected.getName(), "All")) {
            viewModel.passwords.observe(getViewLifecycleOwner(), passwords -> {
                passwordAdapter = new PasswordListAdapter(passwords, requireActivity(), this);
            });
        } else {
            viewModel.passwords.observe(getViewLifecycleOwner(), passwords -> {
                for(Password password: passwords) {
                    if(Objects.equals(password.getGroupId(), passwordSelected.getId())) {
                        passwordList.add(password);
                    }
                }
                passwordAdapter = new PasswordListAdapter(passwordList, requireActivity(), this);
            });
        }

        binding.passwordList.setAdapter(passwordAdapter);
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
