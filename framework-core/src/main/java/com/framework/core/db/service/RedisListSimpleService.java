package com.framework.core.db.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.framework.core.db.dao.RedisListSimpleDao;
import com.framework.core.utils.json.JacksonHelper;

@Service
public class RedisListSimpleService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger( RedisListSimpleService.class );
    
    @Resource
    private RedisListSimpleDao redisListSimpleDao;
    
    /** 
     * 压栈 ：左
     * @param key 
     * @param value 
     * @return 
     */  
    public Long push(String key, Object value) {  
        return redisListSimpleDao.push(key, value);  
    }  
    /** 
     * 出栈 ：左
     * @param key 
     * @return 
     */  
    public String pop(String key) {  
        return redisListSimpleDao.pop(key);
    }  
    public <T>T pop(String key, Class<T> clazz) {  
        return JacksonHelper.json2objThrowRuntime( pop(key), clazz );
    }  
  
    /** 
     * 入队列，右进
     * @param key 
     * @param value 
     * @return 
     */  
    public Long inGroup(String key, Object value) {  
        return redisListSimpleDao.inGroup(key, value);
    }  
  
    /** 
     * 出队列，左出
     *  
     * @param key 
     * @return 
     */  
    public String outGroup(String key) {  
        return redisListSimpleDao.outGroup(key);  
    }  
    public <T>T outGroup(String key, Class<T> clazz) {  
        return redisListSimpleDao.outGroup(key, clazz);
    }  
  
    /** 
     * 栈/队列长 
     *  
     * @param key 
     * @return 
     */  
    public Long length(String key) {  
        return redisListSimpleDao.length(key);  
    }  
  
    /** 
     * 范围检索 
     * @param key 
     * @param start 开始下标
     * @param end  结束下标（含）
     * @return 
     */  
    public List<String> range(String key, int start, int end) {  
        return redisListSimpleDao.range(key, start, end);  
    }  
    public <T>List<T> range(String key, int start, int end, Class<T> clazz) {  
        return redisListSimpleDao.range(key, start, end, clazz);
    }  
    public List<String> rangeAll(String key) {  
        return redisListSimpleDao.rangeAll(key);
    }  
    public <T>List<T> rangeAll(String key, Class<T> clazz) {  
        return redisListSimpleDao.rangeAll(key, clazz);
    }  
  
    /** 
     * 移除 
     * @param key 
     * @param i 
     * @param value value不等不移除 
     */  
    public void remove(String key, long i, Object value) {
        redisListSimpleDao.remove(key, i, value);  
    }  
  
    /** 
     * 检索 
     * @param key 
     * @param index 下标
     * @return 
     */  
    public String index(String key, long index) {  
        return redisListSimpleDao.index(key, index);  
    }  
    public <T>T index(String key, long index, Class<T> clazz) {  
        return redisListSimpleDao.index(key, index, clazz);
    }  
    public <T>T index(String key, long index, TypeReference<T> type) {  
        return redisListSimpleDao.index(key, index, type);
    }  
  
    /** 
     * 置值 
     *  
     * @param key 
     * @param index 
     * @param value 
     */  
    public void set(String key, long index, Object value) {
        redisListSimpleDao.set(key, index, value);  
    }  
    
    /** 
     * 追加
     *  
     * @param key 
     * @param index 
     * @param value 
     */  
    public void append(String key, long index, Object value) {
        String vold = redisListSimpleDao.index(key, index);  
        redisListSimpleDao.set(key, index, vold + value);  
    } 
    public void prepend(String key, long index, Object value) {
        String vold = redisListSimpleDao.index(key, index);  
        redisListSimpleDao.set(key, index, value+vold);  
    } 
  
    /** 
     * 裁剪 ，取start和end中间值保存在原数组中
     *  
     * @param key 
     * @param start 开始下标
     * @param end  结束下标（含）
     */  
    public void trim(String key, long start, int end) {  
        redisListSimpleDao.trim(key, start, end);  
    } 
    
}
