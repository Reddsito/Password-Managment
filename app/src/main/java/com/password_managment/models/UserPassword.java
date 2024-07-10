package com.password_managment.models;

import androidx.annotation.NonNull;

import java.util.List;

public class UserPassword {
    private List<PasswordGroup> passwordGroups;
    private List<Password> passwordsWithoutGroup;

    public UserPassword(List<PasswordGroup> passwordGroups, List<Password> passwordsWithoutGroup) {
        this.passwordGroups = passwordGroups;
        this.passwordsWithoutGroup = passwordsWithoutGroup;
    }

    // Getters y setters
    public List<PasswordGroup> getPasswordGroups() {
        return passwordGroups;
    }

    public void setPasswordGroups(List<PasswordGroup> passwordGroups) {
        this.passwordGroups = passwordGroups;
    }

    public List<Password> getPasswordsWithoutGroup() {
        return passwordsWithoutGroup;
    }

    public void setPasswordsWithoutGroup(List<Password> passwordsWithoutGroup) {
        this.passwordsWithoutGroup = passwordsWithoutGroup;
    }

}
