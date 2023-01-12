package com.usktea.plainold.dtos;

public class ReissuedTokenDto {
    private String accessToken;

    public ReissuedTokenDto() {
    }

    public ReissuedTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
