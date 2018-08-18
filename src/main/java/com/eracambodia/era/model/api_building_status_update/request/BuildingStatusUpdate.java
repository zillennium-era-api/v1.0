package com.eracambodia.era.model.api_building_status_update.request;

public class BuildingStatusUpdate {
    private int ownerId;
    private String tableName;
    private String status;
    private int userId;
    private double bookingPrice;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(double bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    @Override
    public String toString() {
        return "BuildingStatusUpdate{" +
                "ownerId=" + ownerId +
                ", tableName='" + tableName + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", bookingPrice=" + bookingPrice +
                '}';
    }
}
