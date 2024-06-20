package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testRedisConnection() {
        String key = "testKey";
        String value = "testValue";

        redisTemplate.opsForValue().set(key, value);
        String cachedValue = (String) redisTemplate.opsForValue().get(key);

        assertEquals(value, cachedValue);
    }
}
