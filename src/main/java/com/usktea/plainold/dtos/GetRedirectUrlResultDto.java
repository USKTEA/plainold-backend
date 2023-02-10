package com.usktea.plainold.dtos;

public class GetRedirectUrlResultDto {
    private String redirectUrl;

    public GetRedirectUrlResultDto() {
    }

    public GetRedirectUrlResultDto(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
}
