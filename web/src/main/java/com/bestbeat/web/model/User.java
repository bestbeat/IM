package com.bestbeat.web.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

/**
 * @author bestbeat
 * 2019/2/21 13:51
 * description:
 */
@Getter
@Setter
public class User extends org.springframework.security.core.userdetails.User {

    private int id;

    public User(int id,String username,String password,Set<GrantedAuthority> authorities){
        this(id,username,password,true,true,true,true,authorities);
    }

    public User(int id,String username,String password,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired,boolean enabled,Set<GrantedAuthority> authorities){
        super(username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled,authorities);
        this.id = id;
    }

}
