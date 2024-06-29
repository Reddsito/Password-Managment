package com.password_managment.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.password_managment.models.Password;
import com.password_managment.models.User;
import com.password_managment.repository.PasswordRepository;
import com.password_managment.repository.UserRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private final UserRepository userRepository = new UserRepository();
    private final PasswordRepository passwordRepository = new PasswordRepository();

    // Mutable LiveData con prefijo _
    private final MutableLiveData<User> _user = new MutableLiveData<>();
    private final MutableLiveData<List<Password>> _passwords = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(true);
    private final MutableLiveData<String> _userId = new MutableLiveData<>();

    // LiveData públicos sin prefijo
    public LiveData<User> user = _user;
    public LiveData<List<Password>> passwords = _passwords;
    public LiveData<Boolean> loading = _loading;
    public LiveData<String> userId = _userId;

    public void setUserId(String userId) {
        _userId.setValue(userId);
    }

    public void fetchUser() {
        if (userId != null) {
            _loading.setValue(true);
            userRepository.getUser(userId.getValue()).thenAccept(user -> {
                _user.setValue(user);
                if (user != null) {
                    _user.setValue(user);
                    fetchPasswords(user.getId());
                }
            }).exceptionally(e -> {
                _loading.setValue(false); // Manejo de error, pero aún marca loading como false
                return null;
            });
        } else {
            _loading.setValue(false); // No hay userId, marcar la carga como false directamente
        }
    }

    private void fetchPasswords(String userId) {
        passwordRepository.getPasswords(userId).thenAccept(passwords -> {
            _passwords.setValue(passwords);
            _loading.setValue(false); // Finaliza la carga cuando se obtienen las contraseñas
        }).exceptionally(e -> {
            _loading.setValue(false); // Manejo de error, pero aún marca loading como false
            return null;
        });
    }
}
