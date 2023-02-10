package com.usktea.plainold.dtos;

public class KakaoTokenResponse {
    private String token_type;
    private String access_token;
    private Integer expires_in;
    private String refresh_token;
    private Integer refresh_token_expires_in;

    public KakaoTokenResponse() {
    }

    public KakaoTokenResponse(String token_type,
                              String access_token,
                              Integer expires_in,
                              String refresh_token,
                              Integer refresh_token_expires_in) {
        this.token_type = token_type;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
        this.refresh_token_expires_in = refresh_token_expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public Integer getRefresh_token_expires_in() {
        return refresh_token_expires_in;
    }
}
