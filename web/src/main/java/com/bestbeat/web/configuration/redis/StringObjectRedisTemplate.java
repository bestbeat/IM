package com.bestbeat.web.configuration.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @author bestbeat
 * 2019/3/1 11:27
 * description:
 */
public class StringObjectRedisTemplate extends RedisTemplate<String,Object> {

    public StringObjectRedisTemplate(){
        this.setKeySerializer(RedisSerializer.string());
        this.setValueSerializer(RedisSerializer.java());
        this.setHashKeySerializer(RedisSerializer.string());
        this.setHashValueSerializer(RedisSerializer.java());
    }

    public StringObjectRedisTemplate(RedisConnectionFactory connectionFactory){
        this();
        this.setConnectionFactory(connectionFactory);
        this.afterPropertiesSet();
    }

}
