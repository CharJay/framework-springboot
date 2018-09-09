package com.framework.core.db.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.framework.core.utils.json.JacksonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

/**
首先key也是字符串类型，但是key中不能包括边界字符；由于key不是binary safe的字符串，所以像”my key”和”mykey\n”这样包含空格和换行的key是不允许的 
注： 
顺便说一下在redis内部并不限制使用binary字符，这是redis协议限制的。”\r\n”在协议格式中会作为特殊字符。 
redis 1.2以后的协议中部分命令已经开始使用新的协议格式了(比如MSET)。总之目前还是把包含边界字符当成非法的key吧，免得被bug纠缠。 
另外关于key的一个格式约定介绍下，object-type:id:field。比如user:1000:password，blog:xxidxx:title 
还有key的长度最好不要太长。道理很明显占内存啊，而且查找时候相对短key也更慢。 
不过也推荐过短的key，比如u:1000:pwd,这样的。显然没上面的user:1000:password可读性好
 * @author
 *
 */
@Component  
public class RedisSimpleDao {  
  
//    @Autowired  
//    private RedisTemplate<String, T> redisTemplate;  
    @Autowired  
    private StringRedisTemplate stringRedisTemplate;  
    
    /** 
     * 批量删除对应的value 
     *  
     * @param keys 
     */  
    public void remove(final String... keys) {  
        for (String key : keys) {  
            remove(key);  
        }  
    }  
  
    /** 
     * 批量删除key 
     *  
     * @param pattern 
     */  
    public void removePattern(final String pattern) {  
//        Set<Serializable> keys = stringRedisTemplate.keys(pattern);  
        Set<String> keys = stringRedisTemplate.keys(pattern);  
        if (keys.size() > 0)  
            stringRedisTemplate.delete(keys);  
    }  
  
    /** 
     * 删除对应的value 
     *  
     * @param key 
     */  
    public void remove(final String key) {  
        if (exists(key)) {  
            stringRedisTemplate.delete(key);  
        }  
    }  
  
    /** 
     * 判断缓存中是否有对应的value 
     *  
     * @param key 
     * @return 
     */  
    public boolean exists(final String key) {  
        return stringRedisTemplate.hasKey(key);  
    }  
  
    /** 
     * 读取缓存 
     *  
     * @param key 
     * @return 
     */  
    public String get(final String key) {  
        String result = null;  
//        ValueOperations<Serializable, Object> operations = stringRedisTemplate.opsForValue();  
        ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();  
        result = operations.get(key);  
        return result;  
    }
    public List<String> getPattern(final String pattern) {  
        Set<String> keys = stringRedisTemplate.keys(pattern);  
        List<String> result = new ArrayList<>();  
        if (keys.size() > 0) {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            for (String key : keys) {
                result.add(operations.get(key));  
            }
        }
        return result;  
    }
    
    public <T>List<T> getPattern(final String pattern, Class<T> clazz) {  
        Set<String> keys = stringRedisTemplate.keys(pattern);  
        List<T> result = new ArrayList<>();  
        if (keys.size() > 0) {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            for (String key : keys) {
                T tmp = JacksonHelper.json2objThrowRuntime( operations.get(key), clazz );
                result.add(tmp);
            }
        }
        return result;  
    }
    
    public Set<String> keys(final String pattern ) {  
        return stringRedisTemplate.keys(pattern);  
    }
    
    public <K>K get(final String key, Class<K> clazz){
        if(get(key) == null) {
            return null;
        }
        return JacksonHelper.json2objThrowRuntime( get(key), clazz );
    }
    public <K>K get(final String key, TypeReference<K> clazz){
        if(get(key) == null) {
            return null;
        }
        return JacksonHelper.json2objThrowRuntime( get(key), clazz );
    }
  
    /** 
     * 写入缓存 
     *  
     * @param key 
     * @param value 
     * @return 
     */  
    public boolean put(final String key, Object value) {  
        return put( key, value, null );  
    }  
  
    /** 
     * 写入缓存 
     *  
     * @param key 
     * @param value  失效时间单位为秒
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public boolean put(final String key, Object value, Long expireTime) {  
        boolean result = false;  
        try {  
            String json = JacksonHelper.obj2jsonThrowRuntime( value );
            
//            ValueOperations<Serializable, Object> operations = stringRedisTemplate.opsForValue();  
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();  
            operations.set(key, json);  
            if(expireTime!=null){
                stringRedisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            }
            result = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    } 
    /**
     * 只设置值，不改变过期时间
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(final String key, Object value) {  
        boolean result = false;  
        try {  
            String json = JacksonHelper.obj2jsonThrowRuntime( value );
            
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();  
            Long expire = stringRedisTemplate.getExpire(key);
            operations.set(key, json);
            if(expire>0) {
                stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            result = true;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    /**
     * 检查对象是否存在，如果存在返回true，如果不存在，保存对象,返回false
     */
    public boolean existsAndSave(final String key, Object value, Long expireTime){
        if(exists( key )){
            return true;
        }
        put( key, value, expireTime );
        return false;
    }
    public boolean existSaveAndUpdTime(final String key, Object value, Long expireTime){
        if((value = get( key )) != null){
            put(key, value, expireTime);
            return true;
        }
        put( key, value, expireTime );
        return false;
    }
  
}  