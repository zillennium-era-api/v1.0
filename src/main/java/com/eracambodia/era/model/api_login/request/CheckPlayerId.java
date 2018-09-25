package com.eracambodia.era.model.api_login.request;

public class CheckPlayerId {
    private int userId;
    private String playerId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "CheckPlayerId{" +
                "userId=" + userId +
                ", playerId='" + playerId + '\'' +
                '}';
    }
}
