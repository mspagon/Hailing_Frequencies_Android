package com.apt.hailingfrequencies.models;

import java.util.ArrayList;
import java.util.List;

public class ConvUsers {
    private String title;
    private User Admin;
    private List<User> userList;

    public ConvUsers() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ConvUsers(String title, User Admin) {
        this.title = title;
        this.Admin = Admin;
        userList = new ArrayList<User>();
    }

    public ConvUsers(String title, User Admin, List<User> userList) {
        this.title = title;
        this.Admin = Admin;
        this.userList = userList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getAdmin() {
        return Admin;
    }

    public void setAdmin(User admin) {
        Admin = admin;
    }

    // Add user to list
    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser(User user) {
        // if admin then remove user
        // else display only admin can remove users.
    }
}
