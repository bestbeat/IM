package com.bestbeat.web.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bestbeat
 * 2019/2/21 13:51
 * description:
 */
@Data
public class User implements Serializable {

    private int id;
    private String username;
    private String password;

}
