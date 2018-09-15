package com.eracambodia.era.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshToken {
    @JsonProperty("refresh_token")
    private String refreshToken;

    public String getRefresh_token() {
        return refreshToken;
    }

    public void setRefresh_token(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
