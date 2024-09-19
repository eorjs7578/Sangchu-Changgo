package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfiguration {

    private static String API_PREFIX = "/api";

    private final String[] whiteList = {
            API_PREFIX+"/members/auth-email",
            API_PREFIX+"/members/signup",
            API_PREFIX+"/members/social-login",

            // 스웨거 관련 엔드포인트
            API_PREFIX+"/swagger-resources/**",
            API_PREFIX+"/swagger-ui/**",
            API_PREFIX+"/v3/api-docs/**",
            API_PREFIX+"/api-docs/**",
            API_PREFIX+"/error",
            // GET요청 관련 엔드포인트
            API_PREFIX+"/posts/*",
            API_PREFIX+"/posts",
            API_PREFIX+"/posts/*/replies"
    };
}
