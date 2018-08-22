package com.eracambodia.era.model.api_agent_transaction.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class TransactionResponse {
    private String buildingName;
    private double totalCost;
    private String address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
                ", buildingName='" + buildingName + '\'' +
                ", totalCost=" + totalCost +
                ", address='" + address + '\'' +
                ", date=" + date +
                ", status='" + status + '\'' +
                '}';
    }
}
