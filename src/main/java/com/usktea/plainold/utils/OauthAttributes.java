package com.usktea.plainold.utils;

import com.usktea.plainold.dtos.UserProfile;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public enum OauthAttributes {
    KAKAO("kakao") {
        @Override
        public UserProfile of(Map<String, Object> attributes) {
            return UserProfile.builder()
                    .email((String) getKakaoAccount(attributes).get("email"))
                    .nickname((String) getKakaoProfile(attributes).get("nickname"))
                    .build();
        }

        private Map<String, Object> getKakaoProfile(Map<String, Object> attributes) {
            return (Map<String, Object>) getKakaoAccount(attributes).get("profile");
        }

        private Map<String, Object> getKakaoAccount(Map<String, Object> attributes) {
            return (Map<String, Object>) attributes.get("kakao_account");
        }
    };

    private String providerName;

    OauthAttributes(String name) {
        this.providerName = name;
    }

    public static UserProfile extract(String providerName, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> Objects.equals(provider.providerName, providerName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of(attributes);
    }

    public abstract UserProfile of(Map<String, Object> attributes);
}
