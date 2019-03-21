package com.bestbeat.web.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
                .antMatchers("/html/register.html").permitAll()
                .antMatchers("/login/user").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/html/login.html")
                //指定自定义form表单请求的路径
                .loginProcessingUrl("/login/permission")
                .usernameParameter("account")
                .passwordParameter("password")
//                .failureUrl("/login/error")  //失败重定向
                .defaultSuccessUrl("/html/home.html",true) //成功重定向,alwaysUse参数true保留defaultSuccessUrl用于重定向，false不使用defaultSuccessUrl重定向
                .permitAll()
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/img/**");
    }
}
