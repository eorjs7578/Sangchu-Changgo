package com.d108.project.cache.redisEmail.dto;

import lombok.Getter;

@Getter
public class EmailAuthCheckDto {
    private String email;
    private String authCode;
}
