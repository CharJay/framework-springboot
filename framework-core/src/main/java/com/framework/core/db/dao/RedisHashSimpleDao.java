package com.framework.core.db.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisHashSimpleDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Long delete(final String key, String... hashKeys) {
        return stringRedisTemplate.opsForHash().delete(key, hashKeys);
    }

    public Map<Object, Object> remove(final String key) {
        return stringRedisTemplate.opsForHash().entries(key);
    }

    public String get(final String key, String hashKey) {
        return (String) stringRedisTemplate.opsForHash().get(key, hashKey);
    }
    public Integer getInteger(final String key, String hashKey) {
        return Integer.parseInt((String) stringRedisTemplate.opsForHash().get(key, hashKey));
    }
    public Long getLong(final String key, String hashKey) {
        return Long.parseLong((String) stringRedisTemplate.opsForHash().get(key, hashKey));
    }
    public Double getDouble(final String key, String hashKey) {
        return Double.parseDouble((String) stringRedisTemplate.opsForHash().get(key, hashKey));
    }

    public boolean exist(final String key, String hashKey) {
        return stringRedisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public Set<Object> getHashkeys(final String key) {
        return stringRedisTemplate.opsForHash().keys(key);
    }

    public Double addDouble(final String key, String hashKey, double delta) {
        return stringRedisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public Long addLong(final String key, String hashKey, long delta) {
        return stringRedisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    public void put(final String key, String hashKey, Object value) {
        stringRedisTemplate.opsForHash().put(key, hashKey, value);
    }
    public <T> void putAll(final String key, T obj) {
        Map<String, String> m;
        try {
            m = BeanUtils.describe(obj);
            m.remove("class");
            stringRedisTemplate.opsForHash().putAll(key, m);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        
    }
    public <T> void putAll(final String key, Map<String, String> m, Long expireTime) {
        stringRedisTemplate.opsForHash().putAll(key, m);
        if(expireTime != null) {
            stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        }
    }
    public void putAll(final String key, Object obj, Long expireTime) {
        Map<String, String> m;
        try {
            m = BeanUtils.describe(obj);
            m.remove("class");
            stringRedisTemplate.opsForHash().putAll(key, m);
            if(expireTime != null) {
                stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        
    }

    public void putAll(final String key, Map<String, String> m) {
        stringRedisTemplate.opsForHash().putAll(key, m);
    }

    public boolean existAndPut(final String key, String hashKey, Object value) {
        return stringRedisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    public Long size(final String key) {
        return stringRedisTemplate.opsForHash().size(key);
    }

    public List<Object> getHashValue(final String key) {
        return stringRedisTemplate.opsForHash().values(key);
    }

}