package com.d108.project.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration {
    private final String API_PREFIX = "/api";

    private final CorsConfigurationSource corsConfigurationSource;

    private final String[] whiteList = {
            API_PREFIX + "/members/signup", API_PREFIX + "/members/login"
    };

    private final String[] swaggerWhiteList = {
            "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/api-docs/**", "/error",
    };

    private final String[] whiteListForGet = {
            API_PREFIX + "/posts/*,", API_PREFIX + "/posts"
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(cors -> cors.configurationSource(corsConfigurationSource));
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(whiteList).permitAll()
//                        .requestMatchers(swaggerWhiteList).permitAll()
//                        .requestMatchers(HttpMethod.GET, whiteListForGet).permitAll()
//                        .anyRequest().authenticated()
//                );
        return http.build();
    }
}
