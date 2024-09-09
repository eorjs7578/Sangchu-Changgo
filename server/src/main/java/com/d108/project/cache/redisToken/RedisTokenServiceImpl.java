package com.d108.project.cache.redisToken;

import com.d108.project.cache.redis.RedisUtil;
import com.d108.project.config.security.JwtConfiguration;
import com.d108.project.domain.member.dto.MemberLoginResponseDto;
import com.d108.project.domain.member.entity.Member;
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


    public MemberLoginResponseDto getToken(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 회원입니다."));

        String accessToken = config.generateToken(member, 1000*ACCESS_TOKEN_EXPIRE);
        String refreshToken = config.generateToken(member, 1000*REFRESH_TOKEN_EXPIRE);

        // accessToken은 레디스에
        redisUtil.setDataExpire(REDIS_ACCESS_TOKEN_PREFIX + memberId, accessToken, ACCESS_TOKEN_EXPIRE);

        // TODO: refreshToken 은 DB에 저장

        // TODO: Dto 에 담아서 반환

    }
}
