package com.usktea.plainold.applications.oAuth;

import java.util.Map;

public class OAuthServiceFactory {
    private Map<String, OAuthService> services;

    public OAuthServiceFactory(Map<String, OAuthService> services) {
        this.services = services;
    }

    public OAuthService getOAuthService(String provider) {
        return services.get(provider);
    }
}
