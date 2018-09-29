package com.eracambodia.era.model.socket;

public class BuildingUpdate {
    private Integer id;
    private String status;
    private Agent agent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
                "id=" + id +
                ", status='" + status + '\'' +
                ", agent=" + agent +
                '}';
    }
}
