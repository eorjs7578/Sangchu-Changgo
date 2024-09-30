package com.d108.project.cache.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    // Redis에 접근하기 위한 Spring의 Redis 템플릿 클래스
    private final StringRedisTemplate redisTemplate;

    public static long REDIS_EMAIL_EXPIRE = 60 * 5;       // 60sec * 5min
    public static long REDIS_VIEW_EXPIRE = 60 * 60 * 24;  // 60sec * 60min * 24hour

    // 키(key)에 해당하는 데이터를 Redis에서 가져옴
    public String getData(String key) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    // 키(key)에 값을 저장
    public void setData(String key, String value) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);
    }

    // 키(key)에 값을 저장하고, 지정된 시간(duration) 후에 데이터가 만료되도록 설정
    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key,value,expireDuration);
    }

    public void deleteData(String key){ //지정된 키(key)에 해당하는 데이터를 Redis에서 삭제하는 메서드
        redisTemplate.delete(key);
    }

    // 조회수 증가 함수
    public Long incrementView(String key, long ExpireDuration) {
        return redisTemplate.opsForValue().increment(key, 1);
    }
}
