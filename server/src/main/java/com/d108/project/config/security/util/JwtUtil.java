package com.d108.project.config.security.util;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.loginCredential.repository.LoginCredentialRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${spring.jwt.access-token.expire-time}")
    public Long ACCESS_TOKEN_EXPIRE;
    @Value("${spring.jwt.refresh-token.expire-time}")
    public Long REFRESH_TOKEN_EXPIRE;
    
    private final RedisUtil redisUtil;
    private final UserDetailsService userDetailsService;
    private final LoginCredentialRepository loginCredentialRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String REDIS_ACCESS_TOKEN_PREFIX = "auth:accessToken:";


    // JWT 서명에 사용할 비밀키 (application.properties 또는 application.yml에서 주입됨)
    @Value("${spring.jwt.salt}")
    private String jwtSecret;

//    // 서명 키를 생성하는 메서드
//    // Base64로 인코딩된 비밀키를 디코딩하여 Key 객체를 반환함
//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    // 키 생성 방식 변경
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // 주어진 Member 객체를 기반으로 JWT 토큰을 생성하는 메서드
    // 토큰에는 사용자의 사용자 이름(subject), 발행 시간, 만료 시간이 포함됨
    public String generateToken(String username, String tokenType) {
        Long expireTime = 0L;
        if (tokenType.equals("accessToken")) {
            expireTime = ACCESS_TOKEN_EXPIRE;
        } else if (tokenType.equals("refreshToken")) {
            expireTime = REFRESH_TOKEN_EXPIRE;
        }
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 주어진 JWT 토큰을 검증하는 메서드
    // 토큰이 유효하면 true를 반환하고, 그렇지 않으면 관련 예외를 처리함
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // 유효한 JWT 토큰에서 사용자 이름을 추출하는 메서드
    // 토큰을 파싱하여 subject(사용자 이름)를 반환함
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // 서명 키를 사용하여 토큰을 파싱
                .build()
                .parseClaimsJws(token) // JWT의 클레임을 파싱
                .getBody() // JWT의 body 부분(클레임)을 가져옴
                .getSubject(); // 클레임에서 subject(사용자 이름)를 반환
    }

    public String extractToken(
            @NonNull HttpServletRequest request,
            String tokenType
    ) {
        Cookie[] cookies = request.getCookies();
        String token;
        // 쿠키에서 토큰 추출
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(tokenType)) {
                    token = cookie.getValue();
                    return token;
                }
            }
        }
        return null;
    }

    public void deleteTokenOnCookie(
            @NonNull HttpServletResponse response
    ) {
        Cookie cookieRefresh = new Cookie("refresh_token", "");
        cookieRefresh.setMaxAge(0);
        cookieRefresh.setPath("/");
        response.addCookie(cookieRefresh);

        Cookie cookieAccess = new Cookie("refresh_token", "");
        cookieAccess.setMaxAge(0);
        cookieAccess.setPath("/");
        response.addCookie(cookieAccess);
    }

    @Transactional
    public void tokenRefresh(
            @NonNull HttpServletResponse response,
            String refreshToken) throws RuntimeException {
        // 리프레시 토큰에서 추출한 username을 통해 DB에 저장된 리프레시 토큰을 비교
        String storedToken = loginCredentialRepository.findRefreshTokenByUsername(refreshToken)
                .orElse(null);

        // 근데 토큰이 DB에 저장된 것과 다른 경우
        if (!refreshToken.equals(storedToken)) {
            deleteTokenOnCookie(response);
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        // 리프레시 토큰이 확인 되었으면
        // 리프레시 토큰을 통해 새로 토큰을 발급
        String username = getUsernameFromToken(refreshToken);
        getToken(username);
    }

    @Transactional
    public TokenResponseDto getToken(String username) {

        String accessToken = generateToken(username, "accessToken");
        String refreshToken = generateToken(username, "refreshToken");

        try {
            // accessToken은 레디스에
            redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + username, accessToken, ACCESS_TOKEN_EXPIRE);

            // refreshToken 은 DB에 저장
            LoginCredential loginCredential = loginCredentialRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 회원입니다."));
            loginCredential.setRefreshToken(refreshToken);
            loginCredentialRepository.save(loginCredential);

        } catch (Exception e) {
            throw new RuntimeException("토큰 발급 중 오류가 발생했습니다.");
        }

        return TokenResponseDto.from(refreshToken, accessToken);
    }

    public void authenticateWithToken(String token) throws RuntimeException {
        String username = getUsernameFromToken(token);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new RuntimeException("아이디가 존재하지 않습니다.");
        }
    }

    public void pushTokenOnResponse(HttpServletResponse response, String accessToken, String refreshToken) {
        // 생성된 토큰을 쿠키로 만들어서
        Cookie accessTokenCookie = new Cookie("access_token", accessToken);
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        // 각각 
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        // 셋팅해서 넘겨주기
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        response.addCookie(refreshTokenCookie);
    }
}

