package com.d108.project.config.security.filter;

import com.d108.project.config.security.util.JwtUtil;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    // 의존성 주입
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        // 여기는 토큰이 필요하지 않은 API URL의 리스트
        List<String> list = Arrays.asList(
                "/members/login",
                "/members/signup"
        );
        log.info("jwtfilter");
        // 토큰이 필요하지 않은 API의 경우 로직 처리 없이 다음 필터로 이동한다.
        if (list.contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. OPTIONS 요청일 경우 -> 로직 처리 없이 다음 필터로 이동
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 그 외, 토큰이 필요한 경우

        // 1. Client에서 API를 요청할 때 쿠키를 확인한다.
        Cookie[] cookies = request.getCookies();

        String accessToken = null;
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (accessToken == null && "access_token".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                }
                if (refreshToken == null && "refresh_token".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                }
            }
        }

        // 2. 토큰의 유효성을 확인한다.
        try {
            // 2-1. 쿠키내에 토큰이 존재하는지
            if (accessToken != null && !accessToken.equalsIgnoreCase("")) {

                // 2-2. 토큰이 유효한지
                // TODO: 엑세스 토큰이 유효하지 않다면 리프레시 토큰이 유효한지도 검증해서 살려보낼 수 있으면 살려보내기
                if (jwtUtil.isTokenValid(accessToken)) {
                    // 토큰이 유효하다면, 추출한 토큰을 기반으로 사용자 아이디를 반환한다.
                    String loginId = jwtUtil.getUsernameFromToken(accessToken);
                    
                    // 2-3. 사용자 아이디가 존재하는지
                    if (loginId != null && !loginId.equalsIgnoreCase("")) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        filterChain.doFilter(request, response);
                    }
                    // 2-4. 사용자 아이디가 존재하지 않는다면
                    else {
                        throw new RuntimeException("token: 아이디가 존재하지 않습니다.");
                    }
                }
                // 2-5. 토큰이 유효하지 않다면
                else {
                    throw new RuntimeException("token: 유효하지 않은 토큰입니다.");
                }
            }
            // 2-6. 토큰이 존재하지 않는다면
            else {
                throw new RuntimeException("token: 토큰이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}

