package com.d108.project.config.security.oauth2;

import com.d108.project.config.security.oauth2.userInfo.OAuth2UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class OAuth2UserPrincipal implements UserDetails, OAuth2User {

    private final OAuth2UserInfo userInfo;

    public OAuth2UserPrincipal(OAuth2UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public OAuth2UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        String username = userInfo.getId();
        if (userInfo.getProvider().toString().equalsIgnoreCase("kakao")) {
            username = "K@" + username;
        } else if (userInfo.getProvider().toString().equalsIgnoreCase("naver")) {
            username = "N@" + username;
        }
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return userInfo.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return userInfo.getEmail();
    }

    public String getNickname() {
        return userInfo.getAttributes().get("nickname").toString();
    }

    public String getEmail() {
        return userInfo.getEmail();
    }
}
