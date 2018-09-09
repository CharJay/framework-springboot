package com.framework.core.db.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.framework.core.utils.json.JacksonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisSetSimpleDao {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 添加value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long add(String key, Object value) {
        return stringRedisTemplate.opsForSet().add(key, JacksonHelper.obj2jsonThrowRuntime(value));
    }

    /**
     * 添加所有value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long addAll(String key, List<String> value) {
        return stringRedisTemplate.opsForSet().add(key, value.toArray(new String[value.size()]));
    }

    /**
     * 添加所有value。类型是object，使用时会转换，如果是string类型，请使用addAll方法。
     * 
     * @param key
     * @param value
     * @return
     */
    public <T> Long addAll2(String key, List<Object> value) {
        String[] ss = new String[value.size()];
        for (int i = 0; i < value.size(); i++) {
            ss[i] = JacksonHelper.obj2jsonThrowRuntime(ss[i]);
        }
        return stringRedisTemplate.opsForSet().add(key, ss);
    }

    /**
     * 是否set中存在value
     * 
     * @param key
     * @param value
     * @return
     */
    public Boolean isMember(String key, Object value) {
        return stringRedisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 获取所有的成员（String）
     * 
     * @param key
     * @return
     */
    public Set<String> members(String key) {
        return stringRedisTemplate.opsForSet().members(key);
    }

    /**
     * 获取所有的成员（对象）
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> Set<T> members(String key, Class<T> clazz) {
        Set<String> tmp = stringRedisTemplate.opsForSet().members(key);
        Set<T> ret = new HashSet<>();
        for (String str : tmp) {
            ret.add(JacksonHelper.json2objThrowRuntime(str, clazz));
        }
        return ret;
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
        String str = JacksonHelper.obj2jsonThrowRuntime(value);
        return stringRedisTemplate.opsForSet().move(key, str, destKey);
    }

    /**
     * 出栈
     * 
     * @param key
     * @return
     */
    public String pop(String key) {
        return stringRedisTemplate.opsForSet().pop(key);
    }

    /**
     * 出栈
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T pop(String key, Class<T> clazz) {
        String tmp = stringRedisTemplate.opsForSet().pop(key);
        return JacksonHelper.json2objThrowRuntime(tmp, clazz);
    }

    /**
     * 移除某个value
     * 
     * @param key
     * @param value
     * @return
     */
    public Long remove(String key, Object value) {
        return stringRedisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 获取长度
     * 
     * @param key
     * @return
     */
    public Long size(String key) {
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 联合两个key的value
     * 
     * @param key
     * @param otherKeys
     * @return
     */
    public Set<String> union(String key, String otherKeys) {
        return stringRedisTemplate.opsForSet().union(key, otherKeys);
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
        return stringRedisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }
}