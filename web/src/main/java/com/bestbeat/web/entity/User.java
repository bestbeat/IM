package com.bestbeat.web.entity;

import lombok.Data;

/**
 * @author bestbeat
 * @date 2019-8-11
 * @description 用户PO
 */
@Data
public class User {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
