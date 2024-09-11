//package com.d108.project.config.security.interceptor;
//
//import com.d108.project.cache.redisToken.RedisTokenService;
//import com.d108.project.config.security.util.JwtUtil;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//@Component
//@RequiredArgsConstructor
//public class JwtTokenInterceptor implements HandlerInterceptor {
//    // 내 생각에 여기 겹치는 것 같음. 인터셉터 지우고 Filter에 통합
//    private final JwtUtil jwtUtil;
//    private final RedisTokenService redisTokenService;
//
//    @Override
//    public boolean preHandle(
//            HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull Object handler
//    ) {
//        // favicon 에 대한 검증
//        if (request.getRequestURI().equals("/favicon.ico")) {
//            return true;
//        }
//
//        String accessToken = null;
//        String refreshToken = null;
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie: cookies) {
//                if (cookie.getName().equals("access_token")) {
//                    accessToken = cookie.getValue();
//                }
//                if (cookie.getName().equals("refresh_token")) {
//                    refreshToken = cookie.getValue();
//                }
//            }
//        }
//
//        if (accessToken != null && refreshToken != null) {
//            if (jwtUtil.isTokenValid(accessToken)) {
//                String username = jwtUtil.getUsernameFromToken(accessToken);
//                if (username == null) {
//                    throw new RuntimeException("만료되거나 비정상적인 토큰");
//                }
//                return true;
//            } else {
//                // 여기나 밑에 저기서 리프레시 토큰으로 새로 발급
//                if (jwtUtil.isTokenValid(refreshToken)) {
//                    redisTokenService.tokenRefresh(refreshToken);
//                }
//                throw new RuntimeException("유효하지 않은 토큰");
//            }
//        } else {
//            // 토큰이 없는 경우 로그인 페이지로 보내야 함
//            // 만약 로그인 해 있으면 (그럴리가 없을 것 같은데) 쿠키 다 버리고 로그인 시키기
//            throw new RuntimeException("토큰이 없습니다.");
//        }
//    }
//}
