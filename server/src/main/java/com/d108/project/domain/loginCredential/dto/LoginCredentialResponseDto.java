package com.d108.project.domain.loginCredential.dto;

import com.d108.project.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LoginCredentialResponseDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LoginCredentialResponseDto from(Member member) {
        return LoginCredentialResponseDto.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
