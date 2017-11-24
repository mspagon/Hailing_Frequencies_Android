package com.apt.hailingfrequencies.models;

//commOption is in ['web', 'email', 'sms']
//commDetail should be a properly formatted email for email, or phone number for sms, or ??? for web (firebase)

public class ConvUsers {
    private Conversations conv;
    private Users user;
    private String displayName;
    private boolean muted;
    private boolean active;
    private String commOption;
    private String commDetail;

    public ConvUsers() {
    }

    public Conversations getConv() {
        return conv;
    }

    public void setConv(Conversations conv) {
        this.conv = conv;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCommOption() {
        return commOption;
    }

    public void setCommOption(String commOption) {
        this.commOption = commOption;
    }

    public String getCommDetail() {
        return commDetail;
    }

    public void setCommDetail(String commDetail) {
        this.commDetail = commDetail;
    }
}
