package com.eracambodia.era.model.user.swagger;

public class UpdateUserInfo {
    private String username;
    private String phonenumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    @Override
    public String toString() {
        return "UpdateUserInfo{" +
                "username='" + username + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                '}';
    }
}