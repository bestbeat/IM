package com.bestbeat.web.service;

import com.bestbeat.web.dao.mapper.UserMapper;
import com.bestbeat.web.model.Role;
import com.bestbeat.web.model.User;
import com.bestbeat.web.utils.IdUtils;
import com.bestbeat.web.utils.enumeration.RoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 张渠钦
 * 2019/3/8
 * 登录model层
 */
@Service
@Slf4j
public class LoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registerUser(User user){
        Role role = new Role();
        role.setRoleCode(RoleEnum.USER.name());
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoleSet(roleSet);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserId(IdUtils.createUserId());
        userMapper.addUser(user);
        userMapper.addUserRole(user);
        return true;
    }

}
