package com.eracambodia.era.setting;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Default {
    //public static final String oauthTokenUrl="http://localhost:8080/oauth/token";
    public static final String oauthTokenUrl="https://eraapi.herokuapp.com/oauth/token";

    public static final String profilePhoto= ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/image/user/")
            .toUriString();
    public static final String buildingImage=ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/image/building/")
            .toUriString();
}
