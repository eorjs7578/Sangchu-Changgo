package com.d108.project.domain.member.dto;

import lombok.Getter;

@Getter
public class MemberPasswordChangeDto {
    private String oldPassword;
    private String newPassword;
}
