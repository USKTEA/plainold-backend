package com.usktea.plainold.applications.oAuth;

import com.usktea.plainold.applications.token.IssueTokenService;
import com.usktea.plainold.dtos.NaverTokenResponse;
import com.usktea.plainold.dtos.TokenDto;
import com.usktea.plainold.dtos.UserProfile;
import com.usktea.plainold.models.user.Username;
import com.usktea.plainold.models.user.Users;
import com.usktea.plainold.properties.NaverOAuthProperties;
import com.usktea.plainold.repositories.UserRepository;
import com.usktea.plainold.utils.OauthAttributes;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class NaverOAuthService implements OAuthService {
    private final NaverOAuthProperties properties;
    private final IssueTokenService issueTokenService;
    private final UserRepository userRepository;

    public NaverOAuthService(NaverOAuthProperties properties,
                             IssueTokenService issueTokenService,
                             UserRepository userRepository) {
        this.properties = properties;
        this.issueTokenService = issueTokenService;
        this.userRepository = userRepository;
    }

    @Override
    public String getAuthorizationUrl() {
        return properties.getAuthorizationUri() +
                "?response_type=" + properties.getResponseType() +
                "&client_id=" + properties.getClientId() +
                "&state=" + properties.getState() +
                "&redirect_uri=" + properties.getRedirectUri();
    }

    @Override
    public TokenDto login(String code) {
        NaverTokenResponse tokenResponse = getToken(code);

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

    private UserProfile getUserProfile(NaverTokenResponse tokenResponse) {
        Map<String, Object> userAttributes = getUserAttributes(tokenResponse);

        return OauthAttributes.extract(properties.getProvider(), userAttributes);
    }

    private Map<String, Object> getUserAttributes(NaverTokenResponse tokenResponse) {
        return WebClient.create()
                .get()
                .uri(properties.getUserInformationUri())
                .header("Authorization", "Bearer " + tokenResponse.getAccess_token())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();
    }

    private NaverTokenResponse getToken(String code) {
        return WebClient.create()
                .post()
                .uri(properties.getTokenUri())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(tokenRequest(code))
                .retrieve()
                .bodyToMono(NaverTokenResponse.class)
                .block();
    }

    private MultiValueMap<String, String> tokenRequest(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("grant_type", properties.getGrantType());
        formData.add("client_id", properties.getClientId());
        formData.add("client_secret", properties.getClientSecret());
        formData.add("code", code);
        formData.add("state", properties.getState());

        return formData;
    }
}
