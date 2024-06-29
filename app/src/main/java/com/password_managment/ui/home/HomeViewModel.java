package com.password_managment.ui.home;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.password_managment.models.Event;
import com.password_managment.models.Password;
import com.password_managment.models.User;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.repository.UserRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final PasswordRepository passwordRepository = new PasswordRepository();

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    private final MutableLiveData<List<Password>> _passwords = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(true);
    private final MutableLiveData<String> _userId = new MutableLiveData<>();
    private final MutableLiveData<String> _toastMessage = new MutableLiveData<>();
    private final MutableLiveData<Event<Boolean>> _showHome = new MutableLiveData<>();

    public LiveData<User> user = _user;
    public LiveData<List<Password>> passwords = _passwords;
    public LiveData<Boolean> loading = _loading;
    public LiveData<String> userId = _userId;
    public LiveData<String> toastMessage = _toastMessage;
    public LiveData<Event<Boolean>> showHome = _showHome;

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

    public void createPassword(String titleText, String passwordText) {

        if (TextUtils.isEmpty(titleText) || TextUtils.isEmpty(passwordText)) {
            _toastMessage.setValue("No se permiten campos vacios");
            return;
        }

        Password newPassword = new Password(titleText, passwordText);

        passwordRepository.createPassword(userId.getValue(), newPassword)
                .thenAccept(response -> {
                    _showHome.setValue(new Event<Boolean>(true));
                })
                .exceptionally(e -> {
                    Log.e("HomeViewModel", "Error creating password:", e);
                    return null;
                });
    }
}
