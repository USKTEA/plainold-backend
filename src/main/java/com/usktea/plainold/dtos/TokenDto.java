package com.usktea.plainold.dtos;

public class TokenDto {
    private String accessToken;
    private String refreshToken;

    public TokenDto() {
    }

    public TokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static TokenDto fake() {
        return new TokenDto("a.a.a", "b.b.b");
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
