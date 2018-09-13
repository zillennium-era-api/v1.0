package com.eracambodia.era.model.api_building_uuid.response;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class BuildingUUID {
    private int id;
    private String name;
    private String uuid;
    private String status;
    private String type;
    private double totalCost;
    private String countryCode;
    private String countryName;
    private String cityOrProvince;
    private String district;
    private String commune;
    private String village;
    private String streetNameOrNumber;
    private String detail;
    private int numberOfFloor;
    private double buildingHeight;
    private double latitude;
    private double longitude;
    private boolean ifFavorite;
    private int countFavorite;
    private Agent agent;
    private List<File> files;
    private Feature feature;
    private List<Neighborhood> neighborhood;

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
        NumberFormat formatter = new DecimalFormat("#0.00");
        this.totalCost = Double.parseDouble(formatter.format(totalCost));
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityOrProvince() {
        return cityOrProvince;
    }

    public void setCityOrProvince(String cityOrProvince) {
        this.cityOrProvince = cityOrProvince;
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

    public String getStreetNameOrNumber() {
        return streetNameOrNumber;
    }

    public void setStreetNameOrNumber(String streetNameOrNumber) {
        this.streetNameOrNumber = streetNameOrNumber;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(int numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }

    public double getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(double buildingHeight) {
        this.buildingHeight = buildingHeight;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public boolean isIfFavorite() {
        return ifFavorite;
    }

    public void setIfFavorite(boolean ifFavorite) {
        this.ifFavorite = ifFavorite;
    }

    public int getCountFavorite() {
        return countFavorite;
    }

    public void setCountFavorite(int countFavorite) {
        this.countFavorite = countFavorite;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public List<Neighborhood> getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(List<Neighborhood> neighborhood) {
        this.neighborhood = neighborhood;
    }

    @Override
    public String toString() {
        return "BuildingUUID{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", totalCost=" + totalCost +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", cityOrProvince='" + cityOrProvince + '\'' +
                ", district='" + district + '\'' +
                ", commune='" + commune + '\'' +
                ", village='" + village + '\'' +
                ", streetNameOrNumber='" + streetNameOrNumber + '\'' +
                ", detail='" + detail + '\'' +
                ", numberOfFloor=" + numberOfFloor +
                ", buildingHeight='" + buildingHeight + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", ifFavorite=" + ifFavorite +
                ", countFavorite=" + countFavorite +
                ", agent=" + agent +
                ", files=" + files +
                ", feature=" + feature +
                ", neighborhood=" + neighborhood +
                '}';
    }
}
