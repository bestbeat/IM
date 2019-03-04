package com.bestbeat.web.configuration.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author bestbeat
 * 2019/3/1 11:26
 * description:
 */
@Configuration
public class RedisConfig {


    @Bean
    public RedisTemplate stringObjectRedisTemplate(@Autowired RedisConnectionFactory connectionFactory){
        return  new StringObjectRedisTemplate(connectionFactory);
    }


}
