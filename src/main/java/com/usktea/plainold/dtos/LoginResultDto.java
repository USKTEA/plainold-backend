package com.usktea.plainold.dtos;

public class LoginResultDto {
    private String accessToken;

    public LoginResultDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
