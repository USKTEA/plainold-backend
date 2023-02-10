package com.usktea.plainold.applications.oAuth;

import com.usktea.plainold.dtos.TokenDto;

public interface OAuthService {
    String getAuthorizationUrl();

    TokenDto login(String code);
}
