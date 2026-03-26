package com.akshatr.orderService.service.cache;

public interface CacheService {
    public void add(String cacheName, String key, Object value);
    public Object get(String cacheName, String key);
    public void delete(String cacheName, String key);
}
