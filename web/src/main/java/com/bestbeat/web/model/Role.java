package com.bestbeat.web.model;

import lombok.Data;

import java.util.Set;

/**
 * @author 张渠钦
 * 2019/6/19
 * 角色实体类
 */
@Data
public class Role {

    private String roleCode;
    private String roleName;
    private String roleStatus;
    private Set<Permission> permissionSet;

}
