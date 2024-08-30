package com.d108.project.domain.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateDto {
    private String username;
    private String password;
}
