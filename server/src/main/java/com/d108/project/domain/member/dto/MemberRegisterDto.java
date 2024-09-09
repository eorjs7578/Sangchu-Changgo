package com.d108.project.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberRegisterDto {
    private String nickname;
    private String username;
    private String password;
}
