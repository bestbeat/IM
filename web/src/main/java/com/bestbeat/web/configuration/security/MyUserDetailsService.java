package com.bestbeat.web.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author bestbeat
 * 2019/2/19 17:23
 * description:通过用户名查询用户对象用于验证
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        UserDetails userDetails = (UserDetails) redisTemplate.opsForHash().get("user",s);
        if(userDetails != null){
            return userDetails;
        }
        throw new UsernameNotFoundException("username["+s+"] not found");
    }
}
