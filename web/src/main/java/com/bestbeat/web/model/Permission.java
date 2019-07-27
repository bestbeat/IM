package com.bestbeat.web.model;

import lombok.Data;

/**
 * @author 张渠钦
 * 2019/6/19
 * 权限实体类
 */
@Data
public class Permission {

    private String permissionCode;
    private String permissionContent;
    private String permissionStatus;

}
