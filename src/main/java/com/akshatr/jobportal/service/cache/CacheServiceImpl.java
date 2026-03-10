package com.akshatr.jobportal.service.cache;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService{
    private final RedisTemplate<String, Object> redis;

    @Override
    public void add(String cacheName, String key, Object value) {
        redis.opsForHash().put(cacheName, key, value);
    }

    @Override
    public Object get(String cacheName, String key) {
        return redis.opsForHash().get(cacheName, key);
    }

    @Override
    public void delete(String cacheName, String key) {
        redis.opsForHash().delete(cacheName, key);
    }
}
