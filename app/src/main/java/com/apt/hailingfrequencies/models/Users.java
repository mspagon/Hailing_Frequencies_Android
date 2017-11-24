package com.apt.hailingfrequencies.models;

public class Users {
    private String email;
    private String fName;
    private String lName;
    private String joinDate;
    private String prefComm;
    private String prefCommDetail;
    private boolean premium;
    private boolean verified;

    public Users() {
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getPrefComm() {
        return prefComm;
    }

    public void setPrefComm(String prefComm) {
        this.prefComm = prefComm;
    }

    public String getPrefCommDetail() {
        return prefCommDetail;
    }

    public void setPrefCommDetail(String prefCommDetail) {
        this.prefCommDetail = prefCommDetail;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
