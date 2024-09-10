package com.d108.project.config.security.interceptor;

import com.d108.project.config.security.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtTokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler
    ) {
        // favicon 에 대한 검증
        if (request.getRequestURI().equals("/favicon.ico")) {
            return true;
        }
        // TODO: 나는 레디스에서 가져와야한다
        String accessToken = null;
        String refreshToken = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (cookie.getName().equals("access_token")) {
                    accessToken = cookie.getValue();
                }
                if (cookie.getName().equals("refresh_token")) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        if (accessToken != null && refreshToken != null) {
            if (jwtUtil.isTokenValid(accessToken)) {
                String userId = jwtUtil.getUsernameFromToken(accessToken);

                if (userId == null) {
                    throw new RuntimeException("만료되거나 비정상적인 토큰");
                }
                return true;
            } else {
                throw new RuntimeException("유효하지 않은 토큰");
                // TODO: 여기나 밑에 저기서 리프레시 토큰으로 새로 발급 받을 수 있나?
            }
        } else {
            throw new RuntimeException("토큰이 없습니다.");
            // 저기
        }
    }
}
