package com.eracambodia.era.model.api_building_uuid.response;

public class File{
    private int ownerId;
    private String filePath;
    private String type;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "File{" +
                "ownerId=" + ownerId +
                ", filePath='" + filePath + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
