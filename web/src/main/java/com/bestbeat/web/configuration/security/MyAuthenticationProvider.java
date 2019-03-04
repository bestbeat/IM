package com.bestbeat.web.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author bestbeat
 * 2019/2/21 15:26
 * description:
 */
@Slf4j
public class MyAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("UsernamePasswordAuthenticationProvider authentic,{},{}", authentication.getName(), authentication.getPrincipal());
        if(authentication.getPrincipal()!=null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
            if(authentication.getCredentials().equals(userDetails.getPassword())){

                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getCredentials());
            }
        }
        throw new BadCredentialsException("Bad Credentials");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if(aClass.getSimpleName().equals("UsernamePasswordAuthenticationToken")){
            return true;
        }
        return false;
    }
}
