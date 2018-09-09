package com.framework.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Component
@Configuration
public class RedisConfig{
    
    @Autowired
    private RedisConfigBean redisConfigBean;
    
    @Bean
    public JedisPool redisPoolFactory() {
    	RedisConfigBean.Pool pool = redisConfigBean.getPool();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        config.setMaxWaitMillis(pool.getMaxWait());
        config.setTestWhileIdle(pool.isTestWhileIdle());
        config.setTestOnBorrow(pool.isTestOnBorrow());
        config.setTestOnCreate(pool.isTestOnCreate());
        config.setTestOnReturn(pool.isTestOnReturn());
        
        JedisPool jedisPool = new JedisPool(config, redisConfigBean.getHost(), redisConfigBean.getPort(), redisConfigBean.getTimeout(), redisConfigBean.getPassword(), redisConfigBean.getDatabase());
        return jedisPool;
    }
    
    /**
     * spring redis 数据库连接池
     * 
     * @return
     */
    
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setDatabase(redisConfigBean.getDatabase());
        factory.setHostName( redisConfigBean.getHost() );
        factory.setPort( redisConfigBean.getPort() );
        factory.setTimeout( redisConfigBean.getTimeout() ); // 设置连接超时时间  
        factory.setPassword( redisConfigBean.getPassword() );
        return factory;
    }
    
}
