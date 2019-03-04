package com.bestbeat.web.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author bestbeat
 * 2019/2/21 15:08
 * description: 认证管理
 */
@Slf4j
public class MyAuthenticationManager implements AuthenticationManager {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("parent authentic: ,{},{}",authentication.getName(),authentication.getCredentials());
        throw new BadCredentialsException("Bad Credentials");
    }
}
