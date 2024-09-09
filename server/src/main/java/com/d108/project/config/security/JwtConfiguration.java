package com.d108.project.config.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.d108.project.domain.member.entity.Member;
import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import java.util.Date;

@Component
public class JwtConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(JwtConfiguration.class);

    // JWT 서명에 사용할 비밀키 (application.properties 또는 application.yml에서 주입됨)
    @Value("${spring.jwt.salt}")
    private String jwtSecret;

    // 서명 키를 생성하는 메서드
    // Base64로 인코딩된 비밀키를 디코딩하여 Key 객체를 반환함
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 주어진 Member 객체를 기반으로 JWT 토큰을 생성하는 메서드
    // 토큰에는 사용자의 사용자 이름(subject), 발행 시간, 만료 시간이 포함됨
    public String generateToken(Member member, Long expireTime) {
        return Jwts.builder()
                .setSubject(member.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // 주어진 JWT 토큰을 검증하는 메서드
    // 토큰이 유효하면 true를 반환하고, 그렇지 않으면 관련 예외를 처리함
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
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
}
