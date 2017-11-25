package com.apt.hailingfrequencies.models;

import java.sql.Timestamp;
import java.util.List;

public class Conversation {
    private String title;
    private User admin;
    private Message lastMessage;

    public Conversation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


}
