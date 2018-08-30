package com.eracambodia.era.model.api_agent_transaction.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class TransactionResponse {
    private String buildingName;
    private double totalCost;
    private String countryName;
    private String district;
    private String commune;
    private String village;
    private String street;
    private String cityOrProvince;
    private Date date;
    private String status;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "buildingName='" + buildingName + '\'' +
                ", totalCost=" + totalCost +
                ", countryName='" + countryName + '\'' +
                ", district='" + district + '\'' +
                ", commune='" + commune + '\'' +
                ", village='" + village + '\'' +
                ", street='" + street + '\'' +
                ", cityOrProvince='" + cityOrProvince + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
