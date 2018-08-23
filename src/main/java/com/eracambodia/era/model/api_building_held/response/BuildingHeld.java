package com.eracambodia.era.model.api_building_held.response;

import com.eracambodia.era.setting.Default;

public class BuildingHeld {
    private int id;
    private String name;
    private String uuid;
    private String status;
    private String type;
    private double totalCost;
    private String filePath;
    private Agent agent;

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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getFilePath() {
        if(filePath==null)
        return filePath;
        else return Default.buildingImage+filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "BuildingHeld{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", totalCost=" + totalCost +
                ", filePath='" + filePath + '\'' +
                ", agent=" + agent +
                '}';
    }
}
