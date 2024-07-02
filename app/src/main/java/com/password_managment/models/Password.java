package com.password_managment.models;

public class Password {
    private String id;
    private String title;
    private String password;

    public Password() {

    }

    public Password(String title, String password) {
        this.title = title;
        this.password = password;
    }

    public Password(String title, String password, String id) {
        this.title = title;
        this.password = password;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
