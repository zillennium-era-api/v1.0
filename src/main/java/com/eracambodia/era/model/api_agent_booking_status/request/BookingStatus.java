package com.eracambodia.era.model.api_agent_booking_status.request;

public class BookingStatus {
    private int id;
    private int owner_id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BookingStatus{" +
                "id=" + id +
                ", owner_id=" + owner_id +
                ", status='" + status + '\'' +
                '}';
    }
}
