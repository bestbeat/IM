package com.bestbeat.web.model;

import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author bestbeat
 * 2019/2/21 13:51
 * description:
 */
@Data
public class User implements UserDetails, CredentialsContainer {

    private Integer id;
    private String account;
    private String mobilePhone;

    private String username;
    private String password;
    private Set<GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public User(){
        this(-1,null,null,null,null,true,true,true,true,null);
    }

    public User(Integer id,String account,String mobilePhone,String username,String password,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired,boolean enabled,Set<GrantedAuthority> authorities){
        this.id = id;
        this.account = account;
        this.mobilePhone = mobilePhone;
        this.username = username;
        this.password = password;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
