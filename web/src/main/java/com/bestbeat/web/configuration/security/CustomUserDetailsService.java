package com.bestbeat.web.configuration.security;

import com.bestbeat.web.dao.mapper.SecurityMapper;
import com.bestbeat.web.model.Permission;
import com.bestbeat.web.model.Role;
import com.bestbeat.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 张渠钦
 * 2019/2/19 17:23
 * 通过用户名查询用户对象用于验证
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SecurityMapper securityMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = securityMapper.getUserByName(s);

        if (user == null) {
            throw new UsernameNotFoundException("username["+s+"] not found");
        }

        Set<Role> roles = securityMapper.getRolesById(user.getUserId());
        for (Role role : roles) {
            Set<Permission> permissionList = securityMapper.getPermissionsById(role.getRoleCode());
            role.setPermissionSet(permissionList);
        }
        user.setRoleSet(roles);

        Set<GrantedAuthority> roleSet = new HashSet<>();
        for (Role role : user.getRoleSet()){
            roleSet.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleCode().toUpperCase()));
        }

        org.springframework.security.core.userdetails.User securityUser
                = new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),roleSet);

        return securityUser;

    }
}
