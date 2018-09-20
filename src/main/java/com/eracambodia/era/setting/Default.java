package com.eracambodia.era.setting;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Default {
    //public static final String oauthTokenUrl="http://localhost:8080/oauth/token";
    private String a;
    public static final String oauthTokenUrl = "https://eraapi.herokuapp.com/oauth/token";
    public static final String profilePhoto = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/image/user/")
            .toUriString();
    public static final String buildingImage = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/image/building/")
            .toUriString();

    public static final String oneSignalAppID = "fa84ca51-c94d-4de7-9104-f90143f2b946";
    public static final String oneSignalRestAPIKey = "OWY1MmUyODQtMjNhNS00ZjFhLWJlNTktODBkNTdkNGQ4ZDgw";
}
