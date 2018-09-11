package com.eracambodia.era.model.api_noti_to_favoritor;

public class Transaction {
    private Integer userId;
    private String status;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "userId=" + userId +
                ", status='" + status + '\'' +
                '}';
    }
}
