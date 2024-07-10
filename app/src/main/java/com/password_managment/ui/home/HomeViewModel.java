package com.password_managment.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.password_managment.models.Event;
import com.password_managment.models.Password;
import com.password_managment.models.PasswordGroup;
import com.password_managment.models.User;
import com.password_managment.models.UserPassword;
import com.password_managment.repository.PasswordGroupRepository;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class HomeViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final PasswordRepository passwordRepository = new PasswordRepository();
    private final PasswordGroupRepository passwordGroupRepository = new PasswordGroupRepository();

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    private final MutableLiveData<List<Password>> _passwords = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(true);
    private final MutableLiveData<String> _userId = new MutableLiveData<>();
    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _showHome = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _showCreatePassword = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _showCreateGroup = new MutableLiveData<>();
    private final MutableLiveData<Bundle> _passwordData = new MutableLiveData<>();
    private final MutableLiveData<List<String>> _groups = new MutableLiveData<>();
    private final MutableLiveData<Bundle> _showEditPassword = new MutableLiveData<>();
    private final MutableLiveData<List<PasswordGroup>> _passwordGroups = new MutableLiveData<>();

    public LiveData<User> user = _user;
    public LiveData<List<Password>> passwords = _passwords;
    public LiveData<Boolean> loading = _loading;
    public LiveData<String> userId = _userId;
    public LiveData<String> toastMessage = _toastMessage;
    public LiveData<Event<Boolean>> showHome = _showHome;
    public LiveData<Event<Boolean>> showCreatePassword = _showCreatePassword;
    public LiveData<Event<Boolean>> showCreateGroup = _showCreateGroup;
    public LiveData<List<String>> groups = _groups;
    public LiveData<Bundle> passwordData = _passwordData;
    public LiveData<Bundle> showEditPassword = _showEditPassword;
    public LiveData<List<PasswordGroup>> passwordGroups = _passwordGroups;



    public void fetchUser(String userId) {
        if (userId != null) {
            _loading.setValue(true);
            userRepository.getUser(userId).thenAccept(user -> {
                if (user != null) {
                    _user.setValue(user);
                    _userId.setValue(userId);
                    fetchPasswords(userId);
                }
            }).exceptionally(e -> {
                _loading.setValue(false);
                Log.d("HomeViewModel", e.getMessage());
                return null;
            });
        } else {
            _loading.setValue(false);
        }
    }

    private void fetchPasswords(String userId) {
        passwordRepository.getPasswords(userId).thenAccept(passwords -> {
            _loading.setValue(false);
            _passwords.setValue(passwords);
        }).exceptionally(e -> {
            _loading.setValue(false);
            return null;
        });
    }

    public void fetchPasswordGroup(String userId) {
        passwordGroupRepository.getPasswordGroups(userId)
                .thenAccept(_passwordGroups::postValue)
                .exceptionally(e -> {
                    Log.e("HomeViewModel", "Error getting password group: ", e);
                    return null;
                });
    }

    public void createPasswordGroup(String userId, PasswordGroup passwordGroup) {

        if (TextUtils.isEmpty(passwordGroup.getName())) {
            _toastMessage.setValue("No se permiten campos vacios");
            return;
        }

        passwordGroupRepository.createPasswordGroup(passwordGroup, userId).thenAccept(response -> {
            List<PasswordGroup> passwordGroupList;
            if(Objects.requireNonNull(_passwordGroups.getValue()).isEmpty()) {
                passwordGroupList = new ArrayList<>();
            }
            passwordGroupList = _passwordGroups.getValue();
            passwordGroupList.add(passwordGroup);
            _passwordGroups.setValue(passwordGroupList);
            _showHome.setValue(new Event<Boolean>(true));
        }).exceptionally(e -> {
                    Log.e("HomeViewModel", "Error creating password group: ", e);
                    return null;
                });

    }

    public void createPassword(String titleText, String passwordText, String groupId, String groupName) {

        if (TextUtils.isEmpty(titleText) || TextUtils.isEmpty(passwordText)) {
            _toastMessage.setValue("No se permiten campos vacios");
            return;
        }

        Password newPassword = new Password(titleText, passwordText, groupId, groupName);

        passwordRepository.createPassword(userId.getValue(), newPassword)
                .thenAccept(response -> {
                    List<Password> passwordList = _passwords.getValue();
                    passwordList.add(newPassword);
                    _passwords.setValue(passwordList);
                    _showHome.setValue(new Event<Boolean>(true));
                })
                .exceptionally(e -> {
                    Log.e("HomeViewModel", "Error creating password:", e);
                    return null;
                });
    }

    public void showCreatePasswordFragment() {
        Log.d("ViewModel", "ViewModel");
        _showCreatePassword.setValue(new Event<>(true));
    }

    public void showCreateGroupFragment() {
        _showCreateGroup.setValue(new Event<>(true));
    }

    public void showAddGroupFragment(String titleText, String passwordText) {
        if (TextUtils.isEmpty(titleText) || TextUtils.isEmpty(passwordText)) {
            _toastMessage.setValue("No se permiten campos vacios");
            return;
        }
        Bundle data = new Bundle();
        data.putString("title", titleText);
        data.putString("password", passwordText);

        _passwordData.setValue(data);
    }

    public void showEditPasswordFragment(Password password) {
        Bundle data = new Bundle();
        data.putString("title", password.getTitle());
        data.putString("password", password.getPassword());

        _showEditPassword.setValue(data);
    }

    public void showHomeFragment() {
        _showHome.setValue(new Event<Boolean>(true));
    }

}
