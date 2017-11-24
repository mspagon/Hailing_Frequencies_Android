package com.apt.hailingfrequencies.models;


public class Message {
    private String name;
    private String photoUrl;
    private String text;

    // Empty onstructor needed for Firebase
    public Message() {

    }

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public Message(String name, String photoUrl, String text) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.text = text;
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
