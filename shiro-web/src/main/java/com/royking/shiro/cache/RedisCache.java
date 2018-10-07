package com.royking.shiro.cache;

import com.royking.shiro.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author YIN
 * @Title: RedisCache
 * @Package com.royking.shiro.cache
 * @Description:
 * @date 2018/10/717:23
 */
@Component
public class RedisCache<K, V> implements Cache<K, V> {

    @Autowired
    private JedisUtil jedisUtil;

    private final String SHIRO_CACHE_PERFIX = "royking-cache:";

    private byte[] getKey(K k) {
        if (k instanceof String) {
            return (SHIRO_CACHE_PERFIX + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    public V get(K k) throws CacheException {
        System.out.println("从redis中取出角色");
        byte[] value = jedisUtil.get(getKey(k));
        if (value == null) {
            return null;
        }
        return (V) SerializationUtils.deserialize(value);
    }

    public V put(K k, V v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return v;
    }

    public V remove(K k) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = jedisUtil.get(key);
        jedisUtil.del(key);
        if (value == null) {
            return null;
        }
        return (V) SerializationUtils.deserialize(value);
    }

    public void clear() throws CacheException {

    }

    public int size() {
        Set<byte[]> keys = jedisUtil.keys(SHIRO_CACHE_PERFIX);
        if (!CollectionUtils.isEmpty(keys)) {
            return keys.size();
        }
        return 0;
    }

    public Set<K> keys() {
        return null;
    }

    public Collection<V> values() {
        return null;
    }
}
