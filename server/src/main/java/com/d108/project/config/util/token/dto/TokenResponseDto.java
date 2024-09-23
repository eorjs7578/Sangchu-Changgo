package com.d108.project.config.util.token.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
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
