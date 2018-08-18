package com.eracambodia.era.model.api_building_uuid.response;

public class Neighborhood {
    private int ownerId;
    private String name;
    private String distance;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Neighborhood{" +
                "ownerId=" + ownerId +
                ", name='" + name + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
