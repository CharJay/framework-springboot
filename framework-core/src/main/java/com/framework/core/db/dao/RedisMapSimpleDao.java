package com.framework.core.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis类型命令
 * http://zhanghaj00.iteye.com/blog/2047015
 * http://blog.csdn.net/guochunyang/article/details/47317851
 * 高效插入
 * http://www.cnblogs.com/ivictor/p/5446503.html
 * @author
 *
 */
@Component  
public class RedisMapSimpleDao {  
  
    @Autowired  
    private StringRedisTemplate stringRedisTemplate;  
    
  
}  