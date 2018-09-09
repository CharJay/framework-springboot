package com.framework.core.db.service;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.framework.core.db.dao.RedisSimpleDao;

@Service
public class RedisSimpleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( RedisSimpleService.class );
    
    @Resource
    private RedisSimpleDao redisSimpleDao;
    
    /** 
     * 批量删除对应的value 
     *  
     * @param keys 
     */  
    public void remove(final String... keys) {  
        redisSimpleDao.remove( keys );
    }  
  
    /** 
     * 批量删除key 
     *  
     * @param pattern 
     */  
    public void removePattern(final String pattern) {  
        redisSimpleDao.removePattern( pattern );
    }  
  
    /** 
     * 删除对应的value 
     *  
     * @param key 
     */  
    public void remove(final String key) {  
        redisSimpleDao.remove( key );
    }  
  
    /** 
     * 判断缓存中是否有对应的value 
     *  
     * @param key 
     * @return 
     */  
    public boolean exists(final String key) {  
        return redisSimpleDao.exists( key );
    }  
    
    /** 
     * 读取缓存 
     *  
     * @param key 
     * @return 
     */  
    public String get(final String key) {  
        return redisSimpleDao.get( key );  
    }
    public List<String> getPattern(final String pattern) {  
        return redisSimpleDao.getPattern( pattern );  
    }
    public <T>List<T> getPattern(final String pattern, Class<T> clazz) {  
        return redisSimpleDao.getPattern( pattern, clazz );  
    }
    public <T>Set<String> keys(final String pattern) {  
        return redisSimpleDao.keys( pattern );  
    }
    public <T>T get(final String key, Class<T> clazz){
        return (T) redisSimpleDao.get( key, clazz );
    }
  
    /** 
     * 写入缓存 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
    public boolean put(final String key, Object value) {  
        return redisSimpleDao.put( key, value );  
    }  
    public boolean setValue(final String key, Object value) {  
        return redisSimpleDao.setValue( key, value );  
    }  
  
    /** 
     * 写入缓存 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
    public boolean put(final String key, Object value, Long expireTime) {  
        return redisSimpleDao.put( key, value, expireTime );  
    }  
    /**
     * 判断是否存在，，不存在则保存
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean existsAndSave(final String key, Object value, Long expireTime) {  
        return redisSimpleDao.existsAndSave( key, value, expireTime );  
    }  
    public boolean existSaveAndUpdTime(final String key, Object value, Long expireTime) {  
        return redisSimpleDao.existSaveAndUpdTime( key, value, expireTime );  
    }  
    
}
