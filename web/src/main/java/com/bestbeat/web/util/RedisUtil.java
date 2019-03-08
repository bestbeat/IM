package com.bestbeat.web.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author bestbeat
 * 2019/3/8 10:51
 * description:
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public int getSeq(String seqName){
       Integer seq = (Integer) redisTemplate.opsForValue().get(seqName);
       if(seq == null){
            redisTemplate.opsForValue().set(seqName,0);
            return 0;
       }
       return seq;
    }

}
