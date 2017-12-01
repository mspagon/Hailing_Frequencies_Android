package com.apt.hailingfrequencies.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ConversationResponse {
    private String status;
    private List<Conversation> conversations = new ArrayList<Conversation>();

    public String getStatus() {
        return status;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

}
