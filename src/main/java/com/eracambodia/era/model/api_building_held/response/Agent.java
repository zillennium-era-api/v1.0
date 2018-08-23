package com.eracambodia.era.model.api_building_held.response;

import com.eracambodia.era.setting.Default;

public class Agent {
    private int id;
    private String uuid;
    private String name;
    private String profilePhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhoto() {
        if(profilePhoto==null)
        return profilePhoto;
        else return Default.profilePhoto+profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                '}';
    }
}
