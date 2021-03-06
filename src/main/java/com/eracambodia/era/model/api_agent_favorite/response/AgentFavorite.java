package com.eracambodia.era.model.api_agent_favorite.response;

import com.eracambodia.era.setting.Default;

public class AgentFavorite {
    private int id;
    private String name;
    private String uuid;
    private String status;
    private String type;
    private String countryName;
    private String district;
    private String commune;
    private String village;
    private String street;
    private String cityOrProvince;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCityOrProvince() {
        return cityOrProvince;
    }

    public void setCityOrProvince(String cityOrProvince) {
        this.cityOrProvince = cityOrProvince;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getFilePath() {
        if (filePath == null)
            return filePath;
        else return Default.buildingImage + filePath;
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
        return "AgentFavorite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", countryName='" + countryName + '\'' +
                ", district='" + district + '\'' +
                ", commune='" + commune + '\'' +
                ", village='" + village + '\'' +
                ", street='" + street + '\'' +
                ", cityOrProvince='" + cityOrProvince + '\'' +
                ", totalCost=" + totalCost +
                ", filePath='" + filePath + '\'' +
                ", agent=" + agent +
                '}';
    }
}
