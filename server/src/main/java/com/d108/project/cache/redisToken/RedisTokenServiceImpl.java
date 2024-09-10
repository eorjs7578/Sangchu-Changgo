package com.d108.project.cache.redisToken;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.cache.redisToken.dto.TokenResponseDto;
import com.d108.project.config.security.util.JwtUtil;
import com.d108.project.domain.loginCredential.entity.LoginCredential;
import com.d108.project.domain.loginCredential.repository.LoginCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisTokenServiceImpl implements RedisTokenService {

    private final RedisUtil redisUtil;
    private final JwtUtil config;
    private static final String REDIS_ACCESS_TOKEN_PREFIX = "auth:accessToken:";
    private final LoginCredentialRepository loginCredentialRepository;

    // 토큰을 새로 만드는 경우 (로그인 시)
    @Override
    public TokenResponseDto getToken(String username) {

        String accessToken = config.generateToken(username, 1000*config.ACCESS_TOKEN_EXPIRE);
        String refreshToken = config.generateToken(username, 1000*config.REFRESH_TOKEN_EXPIRE);

        // accessToken은 레디스에
        redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + username, accessToken, config.ACCESS_TOKEN_EXPIRE);

        // refreshToken 은 DB에 저장
        LoginCredential loginCredential = loginCredentialRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 회원입니다."));
        loginCredential.setRefreshToken(refreshToken);
        loginCredentialRepository.save(loginCredential);
        return TokenResponseDto.from(refreshToken,accessToken);
    }
    
    // 엑세스 토큰이 유효하지 않은 경우 리프레시 토큰을 통해 엑세스 토큰을 재발급
    public void tokenRefresh(String refreshToken) {
        // 리프레시 토큰은 유효한 경우에
        // TODO: DB와도 같은지 비교해야함
        if (!config.isTokenValid(refreshToken)) {
            // 리프레시 토큰을 뜯어서 username을 다시 얻고
            String username = config.getUsernameFromToken(refreshToken);

            // 엑세스 토큰을 다시 만든 뒤에
            String accessToken = config.generateToken(username, 1000*config.ACCESS_TOKEN_EXPIRE);
            // 레디스에 다시 저장
            redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + username, accessToken, config.ACCESS_TOKEN_EXPIRE);
        }
    }
}
