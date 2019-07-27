package com.bestbeat.web.configuration.security;

import com.alibaba.fastjson.JSON;
import com.bestbeat.web.model.security.SecurityResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张渠钦
 * 2019/06/23
 * 登录认证不通过处理
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        SecurityResponseBody responseBody = new SecurityResponseBody();

        responseBody.setStatus("000");
        responseBody.setMsg("Need Authoriries");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
