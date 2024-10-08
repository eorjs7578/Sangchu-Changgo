package com.d108.project.cache.redisEmail.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MailSendRequestDto {
    private String email;

    public static MailSendRequestDto of(String email) {
        return MailSendRequestDto.builder()
                .email(email)
                .build();
    }
}
