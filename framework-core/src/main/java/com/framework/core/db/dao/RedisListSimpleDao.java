package com.framework.core.db.dao;

import java.util.ArrayList;
import java.util.List;

import com.framework.core.utils.json.JacksonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

@Component  
public class RedisListSimpleDao {  
  
    @Autowired  
    private StringRedisTemplate stringRedisTemplate;  
    
  
    /** 
     * 压栈 ：左
     * @param key 
     * @param value 
     * @return 
     */  
    public Long push(String key, Object value) {  
        return stringRedisTemplate.opsForList().leftPush(key, JacksonHelper.obj2jsonThrowRuntime( value ));
    }  
    /** 
     * 出栈 ：左
     * @param key 
     * @return 
     */  
    public String pop(String key) {  
        return stringRedisTemplate.opsForList().leftPop(key);  
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
        String json = JacksonHelper.obj2jsonThrowRuntime( value );
        return stringRedisTemplate.opsForList().rightPush(key, json);  
    }  
  
    /** 
     * 出队列，左出
     *  
     * @param key 
     * @return 
     */  
    public String outGroup(String key) {  
        return stringRedisTemplate.opsForList().leftPop(key);  
    }  
    public <T>T outGroup(String key, Class<T> clazz) {  
        String json = outGroup(key);  
        return JacksonHelper.json2objThrowRuntime( json, clazz );
    }  
  
    /** 
     * 栈/队列长 
     *  
     * @param key 
     * @return 
     */  
    public Long length(String key) {  
        return stringRedisTemplate.opsForList().size(key);  
    }  
  
    /** 
     * 范围检索 
     * @param key 
     * @param start 开始下标
     * @param end  结束下标（含）
     * @return 
     */  
    public List<String> range(String key, int start, int end) {  
        return stringRedisTemplate.opsForList().range(key, start, end);  
    }  
    public <T>List<T> range(String key, int start, int end, Class<T> clazz) {  
        List<String> list = range(key, start, end);
        List<T> ret = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ret.add( JacksonHelper.json2objThrowRuntime( list.get( i ), clazz ) );
        }
        return ret;
    }  
    public List<String> rangeAll(String key) {  
        return range(key, 0, -1);  
    }  
    public <T>List<T> rangeAll(String key, Class<T> clazz) {  
        return range(key, 0, -1, clazz);  
    }  
  
    /** 
     * 移除 
     * @param key 
     * @param i 
     * @param value value不等不移除 
     */  
    public void remove(String key, long i, Object value) {
        String json = JacksonHelper.obj2jsonThrowRuntime( value );
        stringRedisTemplate.opsForList().remove(key, i, json);  
    }  
  
    /** 
     * 检索 
     * @param key 
     * @param index 下标
     * @return 
     */  
    public String index(String key, long index) {  
        return stringRedisTemplate.opsForList().index(key, index);  
    }  
    public <T>T index(String key, long index, Class<T> clazz) {  
        String json = index( key, index );
        return JacksonHelper.json2objThrowRuntime( json, clazz );
    }  
    public <T>T index(String key, long index, TypeReference<T> type) {  
        String json = index( key, index );
        return JacksonHelper.json2objThrowRuntime( json, type );
    }  
  
    /** 
     * 置值 
     *  
     * @param key 
     * @param index 
     * @param value 
     */  
    public void set(String key, long index, Object value) {
        String str = JacksonHelper.obj2jsonThrowRuntime( value );
        stringRedisTemplate.opsForList().set(key, index, str);  
    }  
  
    /** 
     * 裁剪 ，取start和end中间值保存在原数组中
     *  
     * @param key 
     * @param start 开始下标
     * @param end  结束下标（含）
     */  
    public void trim(String key, long start, int end) {  
        stringRedisTemplate.opsForList().trim(key, start, end);  
    } 
}  