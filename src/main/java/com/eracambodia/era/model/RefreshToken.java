package com.eracambodia.era.model;

public class RefreshToken {
    private String refresh_token;

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
