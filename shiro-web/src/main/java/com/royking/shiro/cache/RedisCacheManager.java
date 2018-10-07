package com.royking.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author YIN
 * @Title: RedisCacheManager
 * @Package com.royking.shiro.cache
 * @Description:
 * @date 2018/10/717:44
 */
public class RedisCacheManager implements CacheManager {

    @Autowired
    private RedisCache redisCache;

    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return redisCache;
    }
}
