package com.bestbeat.web.controller;

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

    @RequestMapping(value="/login/loginHtml",method = RequestMethod.GET)
    public String loginHtml(){
        return "login";
    }
}
