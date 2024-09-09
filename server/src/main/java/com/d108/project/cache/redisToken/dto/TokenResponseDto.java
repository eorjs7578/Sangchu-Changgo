package com.d108.project.cache.redisToken.dto;

import lombok.Builder;

@Builder
public class TokenResponseDto {
    private String refreshToken;
    private String accessToken;

    public static TokenResponseDto from(String refreshToken, String accessToken) {
        return TokenResponseDto.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
}
