package com.d108.project.domain.security.dto;

import com.d108.project.domain.global.enums.RoleType;
import com.d108.project.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SecurityUserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private RoleType roleType;

    public static SecurityUserDto from(Member member) {
        return SecurityUserDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .password(member.getPassword())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .roleType(member.getRoleType())
                .build();
    }
}
