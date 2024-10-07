package com.d108.project.config.security.handler;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.config.util.token.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisUtil redisUtil;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String username = authentication.getName();
        redisUtil.deleteData(TokenUtil.REDIS_ACCESS_TOKEN_PREFIX+username);
    }
}
