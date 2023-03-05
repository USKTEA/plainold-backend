package com.usktea.plainold.dtos;

public class NaverTokenResponse {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expires_in;
    private String error;
    private String error_description;

    public NaverTokenResponse() {
    }

    public NaverTokenResponse(String access_token,
                              String refresh_token,
                              String token_type,
                              Integer expires_in,
                              String error,
                              String error_description) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.error = error;
        this.error_description = error_description;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public String getError() {
        return error;
    }

    public String getError_description() {
        return error_description;
    }
}
