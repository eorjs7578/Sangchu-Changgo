package com.d108.project.config.security.oauth2.userInfo;

import com.d108.project.config.security.oauth2.OAuth2Provider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(
            String registrationId, String accessToken, Map<String, Object> attributes
    ) {
        if (OAuth2Provider.NAVER.getRegistrationId().equals(registrationId)) {
            return new NaverOAuth2UserInfo(accessToken, attributes);
        } else if (OAuth2Provider.KAKAO.getRegistrationId().equals(registrationId)) {
            return new KakaoOAuth2UserInfo(accessToken, attributes);
        } else {
            throw new RuntimeException("Login with " + registrationId + " is not supported");
        }
    }
}
