package com.bestbeat.web.configuration.security;

import com.bestbeat.web.model.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author bestbeat
 * 2019/2/19 17:23
 * description:通过用户名查询用户对象用于验证
 */
public class MyUserDetailsService implements UserDetailsService {

    private RedisTemplate<String,Object> redisTemplate;

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return (UserDetails) redisTemplate.opsForHash().get("user",s);
    }
}
