package com.d108.project.config.security.filter;

import com.d108.project.config.WhiteListConfig;
import com.d108.project.config.security.util.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    // 의존성 주입
    private final JwtUtil jwtUtil;
    private final WhiteListConfig whiteListConfig;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {

        if (Arrays.stream(whiteListConfig.getWhiteList())
                .anyMatch(whiteList -> new AntPathRequestMatcher(whiteList).matches(request))) {
            log.info("JWT FILTER PASS BY WHITELIST");
            filterChain.doFilter(request, response);
            return;
        }

        // OPTION으로 요청한 경우 무시
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 1. 토큰 추출
        String accessToken = jwtUtil.extractToken(request, "access_token");
        String refreshToken = jwtUtil.extractToken(request, "refresh_token");
        // 2. 토큰의 유효성을 확인한다.

        try {
            // 토큰이 제대로 추출된 경우
            if (accessToken != null && refreshToken != null) {
                // 엑세스 토큰이 유효한 경우
                if (jwtUtil.isTokenValid(accessToken)) {
                    // 엑세스 토큰이 유효한데 리프레시 토큰은 유효하지 않은 경우
                    if (!jwtUtil.isTokenValid(refreshToken)) {
                        // TODO: 엑세스 토큰은 유효한 값을 가져왔는데, 리프레시 토큰은 유효하지 않은 경우(DB와 비교해도 같고, 만료된 경우) 새로 만들어 주는게 문제가 될까?
                        jwtUtil.tokenRefresh(response, refreshToken);
                    }
                    jwtUtil.authenticateWithToken(accessToken);
                    filterChain.doFilter(request, response);
                }

                // 엑세스 토큰이 유효하지 않은 경우
                else {

                    // 근데 리프레시 토큰은 유효한 경우
                    if (jwtUtil.isTokenValid(refreshToken)) {
                        jwtUtil.tokenRefresh(response, refreshToken);
                        jwtUtil.authenticateWithToken(refreshToken);
                        filterChain.doFilter(request, response);
                    }
                    // 리프레시 토큰도 유효하지 않은 경우
                    else {
                        jwtUtil.deleteCookie(response);
                        throw new RuntimeException("토큰이 유효하지 않습니다.");
                    }
                }
                // 토큰이 제대로 추출되지 않은 경우
            } else {
                jwtUtil.deleteCookie(response);
                throw new RuntimeException("토큰이 유효하지 않습니다.");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}

