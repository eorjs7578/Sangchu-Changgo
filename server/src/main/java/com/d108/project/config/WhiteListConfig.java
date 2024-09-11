package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfig {

    private final String[] whiteList = {
            "/members/auth-email",
            "/members/signup",
    };
    private final String[] whiteListForGet = {
            "/posts/*",
            "/posts",
            "/posts/*/replies"
    };

    private final String[] swaggerWhiteList = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api-docs/**",
            "/error"
    };

}
