package com.eracambodia.era.model.api_building_status_update.response;

public class BuildingUpdate {
    private String status;
    private Agent agent;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    @Override
    public String toString() {
        return "BuildingUpdate{" +
                "status='" + status + '\'' +
                ", agent=" + agent +
                '}';
    }
}
