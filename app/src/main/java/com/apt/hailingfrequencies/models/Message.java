package com.apt.hailingfrequencies.models;


import java.sql.Timestamp;

public class Message {
    private String name;
    private String photoUrl;
    private String text;
    private Timestamp time;

    // Empty constructor needed for Firebase
    public Message() {

    }

    public Message(String name, String text) {
        this.name = name;
        this.text = text;
        setCurrentTime();
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
