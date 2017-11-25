package com.apt.hailingfrequencies.models;

import java.util.ArrayList;
import java.util.List;

public class ConvMessages {
    private Conversation conversation;
    private List<Message> messageList;

    public ConvMessages() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public ConvMessages(Conversation conversation) {
        this.conversation = conversation;
        messageList = new ArrayList<Message>();
    }

    public ConvMessages(Conversation conversation, List<Message> messageList) {
        this.conversation = conversation;
        this.messageList = messageList;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }

    // Add message to conversation
    public void addMessage(Message message) {
        messageList.add(message);
    }
}
