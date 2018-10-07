package com.royking.shiro.session;

import com.royking.shiro.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author YIN
 * @Title: RedisSessionDao
 * @Package com.royking.shiro.session
 * @Description:
 * @date 2018/10/713:55
 */
@Component
public class RedisSessionDao extends AbstractSessionDAO {

    @Autowired
    private JedisUtil jedisUtil;

    private final String SHIRO_SESSION_PREFIX = "royking-shiro:";

    private byte[] getKey(String key) {
        return (SHIRO_SESSION_PREFIX + key).getBytes();
    }

    private void saveSession (Session session) {
        byte[] key = getKey(session.getId().toString());
        byte[] value = SerializationUtils.serialize(session);
        jedisUtil.set(key,value);
        // 设置过期时间
        jedisUtil.expire(key,600);
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if (sessionId == null) {
            return null;
        }
        byte[] key = getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
        Session session = (Session) SerializationUtils.deserialize(value);
        return session;
    }

    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            byte[] key = getKey(session.getId().toString());
            jedisUtil.del(key);
        }
    }

    public Collection<Session> getActiveSessions() {
        Set<byte[]> setKey = jedisUtil.keys(SHIRO_SESSION_PREFIX);
        Set<Session> sessions = new HashSet<Session>();
        if (CollectionUtils.isEmpty(setKey)) {
            return null;
        }
        for (byte[] key : setKey) {
            byte[] value = jedisUtil.get(key);
            Session session = (Session) SerializationUtils.deserialize(value);
            sessions.add(session);
        }
        return sessions;
    }
}
