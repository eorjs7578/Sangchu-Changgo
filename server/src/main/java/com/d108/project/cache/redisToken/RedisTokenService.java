package com.d108.project.cache.redisToken;

import com.d108.project.cache.redisToken.dto.TokenResponseDto;

public interface RedisTokenService {
    TokenResponseDto getToken(String username);
}
