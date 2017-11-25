package com.apt.hailingfrequencies.models;

import java.sql.Timestamp;
import java.util.List;

public class Conversation{
    private String title;
    private User admin;
    private Message lastMessage;

    public Conversation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Conversation(String title, User admin) {
        this.title = title;
        this.admin = admin;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

}
