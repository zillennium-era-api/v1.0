package com.eracambodia.era.model.api_noti_favoritor;

public class Notification {
    private int buildingID;
    private String title;
    private String content;
    private String bigPicture;

    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int buildingID) {
        this.buildingID = buildingID;
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
                "buildingUUID='" + buildingID + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", image='" + bigPicture + '\'' +
                '}';
    }
}
