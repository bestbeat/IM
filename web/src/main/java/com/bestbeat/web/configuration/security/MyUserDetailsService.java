package com.bestbeat.web.configuration.security;

import com.bestbeat.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

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

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        User user = new User(1000,"zqq",encoder.encode("123"),authorities);
        redisTemplate.opsForHash().put("user","zqq",user);
        UserDetails userDetails = (UserDetails) redisTemplate.opsForHash().get("user",s);
        if(userDetails != null){
            return userDetails;
        }
        throw new UsernameNotFoundException("username["+s+"] not found");
    }
}
