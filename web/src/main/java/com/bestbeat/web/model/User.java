package com.bestbeat.web.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

/**
 * @author bestbeat
 * 2019/2/21 13:51
 * description:
 */
@Data
public class User extends org.springframework.security.core.userdetails.User {

    private int id;

    public User(int id,String username,String password,Set<GrantedAuthority> authorities){
        this(id,username,password,true,true,true,true,authorities);
    }

    public User(int id,String username,String password,boolean accountNonExpired,boolean accountNonLocked,boolean credentialsNonExpired,boolean enabled,Set<GrantedAuthority> authorities){
        super(username,password,accountNonExpired,accountNonLocked,credentialsNonExpired,enabled,authorities);
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
