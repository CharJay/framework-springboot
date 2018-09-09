package com.framework.core.db.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.framework.core.db.dao.RedisHashSimpleDao;

@Service
public class RedisHashSimpleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( RedisHashSimpleService.class );
    
    @Resource
    private RedisHashSimpleDao redisHashSimpleDao;
    
    public Long delete(final String key, String... hashKeys) {
        return redisHashSimpleDao.delete(key, hashKeys);
    }

    public Map<Object, Object> remove(final String key) {
        return redisHashSimpleDao.remove(key);
    }

    public Object get(final String key, String hashKey) {
        return redisHashSimpleDao.get(key, hashKey);
    }
    public Integer getInteger(final String key, String hashKey) {
        return redisHashSimpleDao.getInteger(key, hashKey);
    }
    public Long getLong(final String key, String hashKey) {
        return redisHashSimpleDao.getLong(key, hashKey);
    }
    public Double getDouble(final String key, String hashKey) {
        return redisHashSimpleDao.getDouble(key, hashKey);
    }

    public <T> T get(final String key, String hashKey, Class<T> clazz) {
        return (T) redisHashSimpleDao.get(key, hashKey);
    }

    public boolean exist(final String key, String hashKey) {
        return redisHashSimpleDao.exist(key, hashKey);
    }

    public Set<Object> getHashkeys(final String key) {
        return redisHashSimpleDao.getHashkeys(key);
    }

    public Double addDouble(final String key, String hashKey, double delta) {
        return redisHashSimpleDao.addDouble(key, hashKey, delta);
    }

    public Long addLong(final String key, String hashKey, long delta) {
        return redisHashSimpleDao.addLong(key, hashKey, delta);
    }

    public void put(final String key, String hashKey, Object value) {
        redisHashSimpleDao.put(key, hashKey, value);
    }

    public void putAll(final String key, Map<String, Object> m) {
        redisHashSimpleDao.putAll(key, m);
    }
    public <T> void putAll(final String key, T obj) {
        redisHashSimpleDao.putAll(key, obj);
    }
    public void putAll(final String key, Object obj, Long expireTime) {
        redisHashSimpleDao.putAll(key, obj, expireTime);
    }

    public boolean existAndPut(final String key, String hashKey, Object value) {
        return redisHashSimpleDao.existAndPut(key, hashKey, value);
    }

    public Long size(final String key) {
        return redisHashSimpleDao.size(key);
    }

    public List<Object> getHashValue(final String key) {
        return redisHashSimpleDao.getHashValue(key);
    }  
    
}
