package com.apt.hailingfrequencies.models;

import java.util.Date;

public class Conversation {
    private String destroyDate;
    private int id;
    private String name;

    public Conversation(String destroyDate, int id, String name) {
        this.destroyDate = destroyDate;
        this.id = id;
        this.name = name;
    }

    public Conversation() {
    }

    public String getDestroyDate() {
        return destroyDate;
    }

    public void setDestroyDate(String destroyDate) {
        this.destroyDate = destroyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
