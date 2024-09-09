package com.d108.project.cache.redisToken;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.cache.redisToken.dto.RefreshTokenResponseDto;
import com.d108.project.config.security.JwtConfiguration;
import com.d108.project.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements RedisTokenService {

    private final RedisUtil redisUtil;
    private final JwtConfiguration config;
    private final MemberRepository memberRepository;
    private static final String REDIS_ACCESS_TOKEN_PREFIX = "auth:accessToken:";

    @Value("${spring.jwt.access-token.expire-time}")
    private static Long ACCESS_TOKEN_EXPIRE;
    @Value("${spring.jwt.refresh-token.expire-time}")
    private static Long REFRESH_TOKEN_EXPIRE;

    // 토큰을 새로 만드는 경우 (로그인 시)
    public RefreshTokenResponseDto getToken(String username) {

        String accessToken = config.generateToken(username, 1000*ACCESS_TOKEN_EXPIRE);
        String refreshToken = config.generateToken(username, 1000*REFRESH_TOKEN_EXPIRE);

        // accessToken은 레디스에
        redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + username, accessToken, ACCESS_TOKEN_EXPIRE);

        // TODO: refreshToken 은 DB에 저장

        return RefreshTokenResponseDto.builder()
                .refreshToken(refreshToken)
                .build();
    }

    // 엑세스 토큰이 유효하지 않은 경우 리프레시 토큰을 통해 엑세스 토큰을 재발급
    public void tokenRefresh(String refreshToken) {
        // 리프레시 토큰은 유효한 경우에
        if (config.validateToken(refreshToken)) {
            // 리프레시 토큰을 뜯어서 username을 다시 얻고
            String username = config.getUsernameFromToken(refreshToken);

            // 엑세스 토큰을 다시 만든 뒤에
            String accessToken = config.generateToken(username, 1000*ACCESS_TOKEN_EXPIRE);
            // 레디스에 다시 저장
            redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + username, accessToken, ACCESS_TOKEN_EXPIRE);
        }
    }
}
