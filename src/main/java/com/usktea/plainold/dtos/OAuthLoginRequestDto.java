package com.usktea.plainold.dtos;

public class OAuthLoginRequestDto {
    private String provider;
    private String code;

    public OAuthLoginRequestDto() {
    }

    public String getProvider() {
        return provider;
    }

    public String getCode() {
        return code;
    }
}
