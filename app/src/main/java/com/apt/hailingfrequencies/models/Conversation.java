package com.apt.hailingfrequencies.models;

import java.util.List;

public class Conversation {
    private String title;
    private List<Message> messageList;
    private List<User> userList;

    public Conversation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Conversation(String title, List<Message> messageList, List<User> userList) {
        this.title = title;
        this.messageList = messageList;
        this.userList = userList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
