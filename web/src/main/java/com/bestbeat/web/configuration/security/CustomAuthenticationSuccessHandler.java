package com.bestbeat.web.configuration.security;

import com.alibaba.fastjson.JSON;
import com.bestbeat.web.model.security.SecurityResponseBody;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张渠钦
 * 2019/06/23
 * 登录成功处理
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityResponseBody responseBody = new SecurityResponseBody();

        responseBody.setMsg("Login Success");
        responseBody.setStatus("200");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
