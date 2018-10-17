package com.eracambodia.era.model.api_building_status_update.request;

public class TransactionOwner {
    private String status;
    private Integer userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "TransactionOwner{" +
                "status='" + status + '\'' +
                ", userId=" + userId +
                '}';
    }
}
