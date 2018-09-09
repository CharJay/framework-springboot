package com.framework.core.db.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.framework.core.db.dao.RedisSetSimpleDao;

@Service
public class RedisSetSimpleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSetSimpleService.class);

    @Resource
    private RedisSetSimpleDao   redisSetSimpleDao;

    /**
     * 添加value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long add(String key, Object value) {
        return redisSetSimpleDao.add(key, value);
    }

    /**
     * 添加所有value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long addAll(String key, List<String> value) {
        return redisSetSimpleDao.addAll(key, value);
    }

    /**
     * 添加所有value。类型是object，使用时会转换，如果是string类型，请使用addAll方法。
     * 
     * @param key
     * @param value
     * @return
     */
    public <T> Long addAll2(String key, List<Object> value) {
        return redisSetSimpleDao.addAll2(key, value);
    }

    /**
     * 是否set中存在value
     * 
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, Object value) {
        return redisSetSimpleDao.isMember(key, value);
    }

    /**
     * 获取所有的成员（String）
     * 
     * @param key
     * @return
     */
    public Set<String> members(String key) {
        return redisSetSimpleDao.members(key);
    }

    /**
     * 获取所有的成员（对象）
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> Set<T> members(String key, Class<T> clazz) {
        return redisSetSimpleDao.members(key, clazz);
    }

    /**
     * 移动值，从key移动到destKey
     * 
     * @param key
     * @param value
     * @param destKey
     * @return
     */
    public Boolean move(String key, Object value, String destKey) {
        return redisSetSimpleDao.move(key, value, destKey);
    }

    /**
     * 出栈
     * 
     * @param key
     * @return
     */
    public String pop(String key) {
        return redisSetSimpleDao.pop(key);
    }

    /**
     * 出栈
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T pop(String key, Class<T> clazz) {
        return redisSetSimpleDao.pop(key, clazz);
    }

    /**
     * 移除某个value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, Object value) {
        return redisSetSimpleDao.remove(key, value);
    }

    /**
     * 获取长度
     * 
     * @param key
     * @return
     */
    public Long size(String key) {
        return redisSetSimpleDao.size(key);
    }

    /**
     * 联合两个key的value
     * 
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> union(String key, String otherKeys) {
        return redisSetSimpleDao.union(key, otherKeys);
    }

    /**
     * 联合两个key的value并存在destKey中
     * 
     * @param key
     * @param otherKey
     * @param destKey
     * @return
     */
    public Long unionAndStore(String key, String otherKey, String destKey) {
        return redisSetSimpleDao.unionAndStore(key, otherKey, destKey);
    }

}