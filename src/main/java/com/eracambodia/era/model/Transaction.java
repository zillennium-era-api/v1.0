package com.eracambodia.era.model;

import com.eracambodia.era.model.user.User;

import java.util.Date;

public class Transaction {
    private int id;
    private int ownerId;
    private String tableName;
    private String status;
    private Date createDate;
    private User userId;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", tableName='" + tableName + '\'' +
                ", status='" + status + '\'' +
                ", createDate=" + createDate +
                ", userId=" + userId +
                '}';
    }
}
