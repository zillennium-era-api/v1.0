package com.eracambodia.era.model;

public class BuildingStatusUpdate {
    private int ownerId;
    private String tableName;
    private String status;
    private int agentId;


    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BuildingStatusUpdate{" +
                "ownerId=" + ownerId +
                ", tableName='" + tableName + '\'' +
                ", status='" + status + '\'' +
                ", agentId=" + agentId +
                '}';
    }
}
