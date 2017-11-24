package com.apt.hailingfrequencies.models;


public class Message {
    private String name;
    private String photoUrl;
    private String text;

    // Empty onstructor needed for Firebase
    public Message() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
