package com.eracambodia.era.model.api_building_uuid.response;

public class Feature {
    private int ownerId;
    private String facilities;
    private String amenities;
    private String includeService;
    private String excludeService;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getIncludeService() {
        return includeService;
    }

    public void setIncludeService(String includeService) {
        this.includeService = includeService;
    }

    public String getExcludeService() {
        return excludeService;
    }

    public void setExcludeService(String excludeService) {
        this.excludeService = excludeService;
    }

    @Override
    public String toString() {
        return "Feature{" +
                "ownerId=" + ownerId +
                ", facilities='" + facilities + '\'' +
                ", amenities='" + amenities + '\'' +
                ", includeService='" + includeService + '\'' +
                ", excludeService='" + excludeService + '\'' +
                '}';
    }
}
