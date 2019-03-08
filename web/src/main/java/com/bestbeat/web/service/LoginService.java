package com.bestbeat.web.service;

import com.bestbeat.web.model.User;
import com.bestbeat.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author bestbeat
 * 2019/3/8 10:40
 * description:
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    private PasswordEncoder encoder =new  BCryptPasswordEncoder();

    public boolean registerUser(User user){
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        user.setAuthorities(authorities);
        user.setId(redisUtil.getSeq("seqUserId"));
        user.setPassword(encoder.encode(user.getPassword()));
        log.info("{}",user);
        redisTemplate.opsForHash().put("user",user.getUsername(),user);
        return true;
    }

}
