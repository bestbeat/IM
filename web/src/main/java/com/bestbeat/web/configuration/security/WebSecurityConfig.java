package com.bestbeat.web.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author bestbeat
 * 2019/2/19 17:05
 * description:
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private UserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder())
                ;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/html/register.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/html/login.html")
//                .failureForwardUrl("/login/error")  //使用forward的方式，能拿到具体失败的原因,并且会将错误信息以SPRING_SECURITY_LAST_EXCEPTION的key的形式将AuthenticationException对象保存到request域中
//                .failureUrl("/login/error")  //失败重定向,拿不到具体失败的原因
                .defaultSuccessUrl("/login/sss")
                .permitAll();
    }


}
