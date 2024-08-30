package com.d108.project.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.d108.project.domain.user.User;

import java.security.Key;
import java.util.Date;

@Component
public class jwtConfiguration {

//    private static final Logger log = LoggerFactory.getLogger(jwtConfiguration.class);
//    private final Key key;
//
//    @Value("${jwt.expiration}")
//    private Long expiration;
//
//    // Key 객체를 사용하여 비밀 키를 설정
//    public jwtConfiguration(@Value("${jwt.secret}") String secret) {
//        log.info("JWT Secret: {}", secret);
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    // JWT 토큰 생성
//    public String generateToken(User user) {
//        return Jwts.builder()
//                .setSubject(user.getId().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(key, SignatureAlgorithm.HS512)
//                .compact();
//
//    }
//
//    // JWT 토큰의 유효성을 검증
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(key)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            // 예외 처리 및 로그 기록
//            return false;
//        }
//    }
//
//    // 토큰에서 클레임(Claims) 추출
//    public String getSubject(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(key)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
}
