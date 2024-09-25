package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfiguration {

    private static String API_PREFIX = "/api";

    private final String[] whiteList = {
            API_PREFIX + "/members/auth-email",
            API_PREFIX + "/members/signup",
            API_PREFIX + "/members/social-login",
            API_PREFIX + "/map/**",

            // 스웨거 관련 엔드포인트
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/error",

            // GET요청 관련 엔드포인트
            API_PREFIX + "/posts/*",
            API_PREFIX + "/posts",
            API_PREFIX + "/posts/*/replies",

            // 상권 분석 관련 앤드 포인트
            API_PREFIX + "/areas/analytic/**",
            API_PREFIX + "/dong/analytic/**",
            API_PREFIX + "/gu/analytic/**"

    };
}
