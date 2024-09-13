package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfiguration {

    private final String[] whiteList = {
            // 로그인 관련 엔드포인트
            "/members/auth-email",
            "/members/signup",
            // 스웨거 관련 엔드포인트
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/error",
            // GET요청 관련 엔드포인트
            "/posts/*",
            "/posts",
            "/posts/*/replies"
    };
}
