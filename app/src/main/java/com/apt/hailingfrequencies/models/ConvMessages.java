package com.apt.hailingfrequencies.models;

import org.json.JSONObject;

import java.util.List;

public class ConvMessages {
    private Users user;
    private String alias;
    private Conversations conv;
    private String postDate;
    private List<JSONObject> edits;
    private boolean deleted;

    public ConvMessages() {
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Conversations getConv() {
        return conv;
    }

    public void setConv(Conversations conv) {
        this.conv = conv;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List<JSONObject> getEdits() {
        return edits;
    }

    public void setEdits(List<JSONObject> edits) {
        this.edits = edits;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
