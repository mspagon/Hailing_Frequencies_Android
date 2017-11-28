package com.apt.hailingfrequencies.models;


import java.sql.Timestamp;

public class Message {
    private String username;
    private String photoUrl;
    private String text;
    private Timestamp time;

    // Empty constructor needed for Firebase
    public Message() {

    }

    public Message(String username, String text) {
        this.username = username;
        this.text = text;
        setCurrentTime();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTime() {
        return time.toString();
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setCurrentTime() {
        setTime(new Timestamp(System.currentTimeMillis()));
    }
}
