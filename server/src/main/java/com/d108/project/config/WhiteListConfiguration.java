package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfiguration {

    private static String API_PREFIX = "/api";

    private final String[] whiteList = {
            // 로그인 관련 엔드포인트
            API_PREFIX+"/members/auth-email",
            API_PREFIX+"/members/signup",
            API_PREFIX+"/members/social-login",
            API_PREFIX+"/login/**",
            "/login/oauth2/code/kakao",
            "/login/oauth2/code/naver",
            "/oauth2/authorization/kakao",
            "/oauth2/authorization/naver",
            // 기본 설정 관련
            "/favicon.ico"
    };

    private final String[] whiteListForSwagger = {
            // 스웨거 관련 엔드포인트
            "/v1/swagger-ui/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v1/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/error",
    };

    private final String[] whiteListForGet = {
            // GET요청 관련 엔드포인트
            API_PREFIX+"/posts/*",
            API_PREFIX+"/posts",
            API_PREFIX+"/posts/*/replies",
    };
}