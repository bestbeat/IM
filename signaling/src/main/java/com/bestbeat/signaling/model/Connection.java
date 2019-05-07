package com.bestbeat.signaling.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接对象
 * @author zhangqq
 */
@Data
public class Connection {

    /**
     * 连接密钥
     */
    private String connKey;

    /**
     * 构成连接的节点
     */
    private List<ConnNode> nodes = new ArrayList<>();
    /**
     * 连接的状态
     */
    private String status;

}
