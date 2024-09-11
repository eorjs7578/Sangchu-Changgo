package com.d108.project.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WhiteListConfig {
    private final String API_PREFIX = "/api";

    private final String[] whiteList = {
            API_PREFIX + "/members/auth-email",
            API_PREFIX + "/members/signup",
            API_PREFIX + "/members/login"
    };
    private final String[] whiteListForGet = {
            API_PREFIX + "/posts/*,", API_PREFIX + "/posts"
    };

    private final String[] swaggerWhiteList = {
            "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/error",
    };

}
