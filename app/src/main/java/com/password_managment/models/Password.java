package com.password_managment.models;

public class Password {
    private String id;
    private String title;
    private String password;
    private String groupId;
    private String groupName;

    public Password() {

    }

    public Password(String title, String password) {
        this.title = title;
        this.password = password;
    }

    public Password(String title, String password, String groupId) {
        this.title = title;
        this.password = password;
        this.groupId = groupId;
    }

    public Password(String title, String password, String groupId, String groupName) {
        this.title = title;
        this.password = password;
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
