package com.bestbeat.web.model.security;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 张渠钦
 * 2019/06/23
 * 安全认证信息-响应载体
 */
@Data
public class SecurityResponseBody implements Serializable {

    private String status;
    private String msg;
    private Object data;

}
