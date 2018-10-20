package com.eracambodia.era.model.socket;

public class BuildingUpdate {
    private int id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BuildingUpdate{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", agent=" + agent +
                '}';
    }
}
