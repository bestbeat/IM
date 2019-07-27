package com.bestbeat.web.configuration.security;

import com.alibaba.fastjson.JSON;
import com.bestbeat.web.model.security.SecurityResponseBody;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 张渠钦
 * 2019/06/23
 * 无权访问
 */
@Component
public class CustomDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        SecurityResponseBody responseBody = new SecurityResponseBody();

        responseBody.setStatus("300");
        responseBody.setMsg("Need Authorities");

        httpServletResponse.getWriter().write(JSON.toJSONString(responseBody));
    }
}
