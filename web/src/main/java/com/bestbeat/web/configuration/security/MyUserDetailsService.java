package com.bestbeat.web.configuration.security;

import com.bestbeat.web.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author bestbeat
 * 2019/2/19 17:23
 * description:通过用户名查询用户对象用于验证
 */
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        User user = new User();
        user.setId(1000);
        user.setUsername("zqq");
        user.setPassword("123");
        redisTemplate.opsForHash().put("user","zqq",user);
        User redisUser = (User) redisTemplate.opsForHash().get("user",s);
        org.springframework.security.core.userdetails.User userDetails =new  org.springframework.security.core.userdetails.User(redisUser.getUsername(),redisUser.getPassword(),null);
        return userDetails;
    }
}
