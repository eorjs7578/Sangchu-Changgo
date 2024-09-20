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
            username = "N@" + username.substring(0,10);
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
        // 임시 닉네임 발급
        String nickname = userInfo.getNickname();
        if (userInfo.getProvider().toString().equalsIgnoreCase("kakao")) {
            nickname = "kakao@" + nickname;
        } else if (userInfo.getProvider().toString().equalsIgnoreCase("naver")) {
            nickname = "naver@" + nickname;
        }
        return nickname;
    }

    public String getEmail() {
        return userInfo.getEmail();
    }
}
