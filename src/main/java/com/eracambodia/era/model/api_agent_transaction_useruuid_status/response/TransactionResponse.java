package com.eracambodia.era.model.api_agent_transaction_useruuid_status.response;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class TransactionResponse {
    private String name;
    private Double totalCost;
    private String countryName;
    private String district;
    private String commune;
    private String village;
    private String street;
    private String cityOrProvince;
    private Date date;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String buildingName) {
        this.name = buildingName;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        this.totalCost = Double.parseDouble(formatter.format(totalCost));
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

    public Long getDate() {
        return date.getTime();
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
                "buildingName='" + name + '\'' +
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
