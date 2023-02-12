package com.usktea.plainold.configs;

import com.usktea.plainold.applications.oAuth.KakaoOAuthService;
import com.usktea.plainold.applications.oAuth.OAuthService;
import com.usktea.plainold.applications.oAuth.OAuthServiceFactory;
import com.usktea.plainold.applications.token.IssueTokenService;
import com.usktea.plainold.properties.KakaoOAuthProperties;
import com.usktea.plainold.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OAuthConfig {
    @Autowired
    private IssueTokenService issueTokenService;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public OAuthServiceFactory oAuthServiceFactory() {
        Map<String, OAuthService> services = new HashMap<>();

        services.put("kakao", new KakaoOAuthService(
                kakaoOAuthProperties(), issueTokenService, userRepository));

        return new OAuthServiceFactory(services);
    }

    @Bean
    public KakaoOAuthProperties kakaoOAuthProperties() {
        return new KakaoOAuthProperties();
    }
}
