package com.royking.shiro.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @author YIN
 * @Title: JedisUtil
 * @Package com.royking.shiro.util
 * @Description:
 * @date 2018/10/713:57
 */
@Component
public class JedisUtil {
    @Autowired
    private JedisPool jedisPool;

    private Jedis getResoure() {
        return jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
        Jedis jedis = getResoure();
        try {
            jedis.set(key,value);
        } finally {
            jedis.close();
        }
        return value;
    }

    public void expire(byte[] key, int i) {
        Jedis jedis = getResoure();
        try {
            jedis.expire(key,i);
        } finally {
            jedis.close();
        }
    }

    public byte[] get(byte[] key) {
        Jedis jedis = getResoure();
        try {
            return jedis.get(key);
        } finally {
            jedis.close();
        }
    }

    public void del(byte[] key) {
        Jedis jedis = getResoure();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getResoure();
        try {
            return jedis.keys((shiro_session_prefix + "*").getBytes());
        } finally {
            jedis.close();
        }
    }
}
