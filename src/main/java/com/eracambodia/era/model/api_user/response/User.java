package com.eracambodia.era.model.api_user.response;

import com.eracambodia.era.setting.Default;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private int role;
    private String uuid;
    private String email;
    private String phone;
    private String idCard;
    private String profilePhoto;
    private boolean enable;

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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhoto() {
        if (profilePhoto == null)
            return profilePhoto;
        else return Default.profilePhoto + profilePhoto;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", uuid='" + uuid + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", enable=" + enable +
                '}';
    }
}
