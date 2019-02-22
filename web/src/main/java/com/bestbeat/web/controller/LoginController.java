package com.bestbeat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  bestbeat
 * 2019/2/18 17:20
 * description:
 */
@RestController
public class LoginController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value="/login/loginHtml",method = RequestMethod.GET)
    public String loginHtml(){
        stringRedisTemplate.opsForHash().put("testRedis","id","hello redis!");
        return "login";
    }

    @RequestMapping("/ddd")
    public String getRedisCache(){
        return (String) stringRedisTemplate.opsForHash().get("testRedis","id");
    }
}
