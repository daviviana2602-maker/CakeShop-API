package br.com.davi.spring_boot_first.security;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;


@Service
public class RateLimitService {

    private final RedisTemplate<String, String> redisTemplate;


    public RateLimitService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public boolean allowRequest(String key, int limit, long duration) {

        Long count = redisTemplate
                .opsForValue()
                .increment(key);


        if (count == 1) {
            redisTemplate.expire(
                    key,
                    Duration.ofSeconds(duration)
            );
        }

        return count <= limit;

    }

}