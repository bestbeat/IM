package com.bestbeat.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
    public UsernamePasswordAuthenticationFilter authenticationFilter(@Autowired AuthenticationManager authenticationManager){
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);
        return authenticationFilter;
    }

    @Bean
    public UserDetailsService myUserDetailsService() {
        return new MyUserDetailsService();
    }


    @Bean
    public AuthenticationManager myAuthenticationManager(){
        return new MyAuthenticationManager();
    }

}
