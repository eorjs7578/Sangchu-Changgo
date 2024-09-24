package com.d108.project.config.security.oauth2.userInfo;

import com.d108.project.config.security.oauth2.OAuth2Provider;

import java.util.Map;

public interface OAuth2UserInfo {
    OAuth2Provider getProvider();

    String getAccessToken();

    Map<String, Object> getAttributes();

    String getId();

    String getEmail();

    String getName();

    String getFirstName();

    String getLastName();

    String getNickname();

    String getProfileImageUrl();
}
