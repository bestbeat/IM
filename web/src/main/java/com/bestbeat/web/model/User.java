package com.bestbeat.web.model;

import lombok.Data;

import java.util.Set;

/**
 * @author 张渠钦
 * 2019/6/19
 * 用户实体类
 */
@Data
public class User  {

    private String userId;
    private String userName;
    private String loginName;

    private String mobilePhone;
    private String password;
    private Set<Role>  roleSet;

}
