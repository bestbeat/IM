package com.bestbeat.web.controller;

import com.bestbeat.web.model.User;
import com.bestbeat.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


/**
 * @author  张渠钦
 * 2019/2/18
 * 登录controller层
 */
@Controller
@RequestMapping("/backend/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    @ResponseBody
    public boolean registerUser(@RequestBody User user){
        log.info("{}",user);
        return loginService.registerUser(user);
    }

}
