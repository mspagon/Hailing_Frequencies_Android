package com.apt.hailingfrequencies.models;

import java.util.List;

public class Conversations {

    private Users owner;
    private String name;
    private ConvMessages messages;
    private String password;
    private String createDate;
    private String destroyDate;
    private List<ConvUsers> aliases;
    private String idPolicy;
    private boolean viewAfterExpire;
    private boolean revealOwner;

    public Conversations() {
    }

    private String restrictComms;

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ConvMessages getMessages() {
        return messages;
    }

    public void setMessages(ConvMessages messages) {
        this.messages = messages;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDestroyDate() {
        return destroyDate;
    }

    public void setDestroyDate(String destroyDate) {
        this.destroyDate = destroyDate;
    }

    public List<ConvUsers> getAliases() {
        return aliases;
    }

    public void setAliases(List<ConvUsers> aliases) {
        this.aliases = aliases;
    }

    public String getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(String idPolicy) {
        this.idPolicy = idPolicy;
    }

    public boolean isViewAfterExpire() {
        return viewAfterExpire;
    }

    public void setViewAfterExpire(boolean viewAfterExpire) {
        this.viewAfterExpire = viewAfterExpire;
    }

    public boolean isRevealOwner() {
        return revealOwner;
    }

    public void setRevealOwner(boolean revealOwner) {
        this.revealOwner = revealOwner;
    }

    public String getRestrictComms() {
        return restrictComms;
    }

    public void setRestrictComms(String restrictComms) {
        this.restrictComms = restrictComms;
    }
}
