package com.password_managment.models;

import androidx.annotation.NonNull;

import java.util.List;

public class PasswordGroup {
    private String id;
    private String name;
    private String description;
    private List<Password> passwords;

    public PasswordGroup() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Password> getPasswords() {
        return passwords;
    }

    public void setPasswords(List<Password> passwords) {
        this.passwords = passwords;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
