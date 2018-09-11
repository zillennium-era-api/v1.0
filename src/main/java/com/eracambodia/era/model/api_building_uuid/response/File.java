package com.eracambodia.era.model.api_building_uuid.response;

import com.eracambodia.era.setting.Default;

public class File {
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
        if (filePath == null)
            return filePath;
        else return Default.buildingImage + filePath;
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
