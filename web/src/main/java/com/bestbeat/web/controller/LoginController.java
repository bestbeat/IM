package com.bestbeat.web.controller;

import com.bestbeat.web.model.User;
import com.bestbeat.web.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author  bestbeat
 * 2019/2/18 17:20
 * description:
 */
@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/permission" ,method = RequestMethod.POST)
    public void home(HttpServletResponse response){
        try {
            response.sendRedirect("/html/home.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public boolean registerUser(User user){
        log.info("{}",user);
        return loginService.registerUser(user);
    }
}
