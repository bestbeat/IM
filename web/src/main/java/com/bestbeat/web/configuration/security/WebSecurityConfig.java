package com.bestbeat.web.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bestbeat
 * 2019/2/19 17:05
 * description:
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
                .failureForwardUrl("/login/loginHtml")
                .failureUrl("/ddd")
                .defaultSuccessUrl("/sss")
                .permitAll();
    }

    @Bean
    public ProviderManager myAuthenticationManager(@Autowired  RedisTemplate<String,Object> redisTemplate){
        List<AuthenticationProvider> providerList = new ArrayList<>();
        MyAuthenticationProvider myProvider = new MyAuthenticationProvider();
        MyUserDetailsService userDetailsService = new MyUserDetailsService();
        userDetailsService.setRedisTemplate(redisTemplate);
        myProvider.setUserDetailsService(userDetailsService);
        providerList.add(myProvider);
        ProviderManager providerManager = new ProviderManager(providerList,new MyAuthenticationManager());
        return providerManager;
    }

}
