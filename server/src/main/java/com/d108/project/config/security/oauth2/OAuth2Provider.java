package com.d108.project.config.security.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuth2Provider {
    NAVER("naver"),
    KAKAO("kakao");

    private final String registrationId;
}
