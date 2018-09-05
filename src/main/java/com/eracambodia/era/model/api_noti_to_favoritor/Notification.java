package com.eracambodia.era.model.api_noti_to_favoritor;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Notification {
    private String buildingUUID;
    private String title;
    private String content;
    private String bigPicture;

    public String getBuildingUUID() {
        return buildingUUID;
    }

    public void setBuildingUUID(String buildingUUID) {
        this.buildingUUID = buildingUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "buildingUUID='" + buildingUUID + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + bigPicture + '\'' +
                '}';
    }
}
