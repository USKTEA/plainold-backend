package com.usktea.plainold.applications.oAuth;

import com.usktea.plainold.applications.token.IssueTokenService;
import com.usktea.plainold.dtos.KakaoTokenResponse;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.dtos.UserProfile;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.properties.KakaoOAuthProperties;
import com.usktea.plainold.repositories.UserRepository;
import com.usktea.plainold.utils.OauthAttributes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Objects;

@Service
public class KakaoOAuthService implements OAuthService {
    private final KakaoOAuthProperties properties;
    private final IssueTokenService issueTokenService;
    private final UserRepository userRepository;

    public KakaoOAuthService(KakaoOAuthProperties properties,
                             IssueTokenService issueTokenService,
                             UserRepository userRepository) {
        this.properties = properties;
        this.issueTokenService = issueTokenService;
        this.userRepository = userRepository;
    }

    @Override
    public String getAuthorizationUrl() {
        return properties.getAuthorizationUri() +
                "?client_id=" + properties.getClientId() +
                "&redirect_uri=" + properties.getRedirectUri() +
                "&response_type=" + properties.getResponseType();
    }

    @Override
    public TokenDto login(String code) {
        KakaoTokenResponse tokenResponse = getToken(code);

        UserProfile userProfile = getUserProfile(tokenResponse);

        Username username = new Username(userProfile.getEmail());

        Users found = userRepository.findByUsername(username).orElse(null);

        if (Objects.isNull(found)) {
            Users newUser = Users.of(userProfile);

            userRepository.save(newUser);
        }

        TokenDto tokens = issueTokenService.issue(username);

        return tokens;
    }

    private UserProfile getUserProfile(KakaoTokenResponse tokenResponse) {
        Map<String, Object> userAttributes = getUserAttributes(tokenResponse);

        return OauthAttributes.extract(properties.getProvider(), userAttributes);
    }

    private Map<String, Object> getUserAttributes(KakaoTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(properties.getUserInformationUri())
                .header("Authorization", "Bearer " + tokenResponse.getAccess_token())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    private KakaoTokenResponse getToken(String code) {
        return WebClient.create()
                .post()
                .uri(properties.getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(tokenRequest(code))
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", properties.getGrantType());
        formData.add("client_id", properties.getClientId());
        formData.add("redirect_uri", properties.getRedirectUri());
        formData.add("code", code);
        formData.add("client_secret", properties.getClientSecret());

        return formData;
    }
}
