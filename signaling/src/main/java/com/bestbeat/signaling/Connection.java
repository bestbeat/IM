package com.bestbeat.signaling;

import lombok.Data;

/**
 * 连接对象
 * @author zhangqq
 */
@Data
public class Connection {

    /**
     * 构成连接的节点
     */
    private ConnNode[] nodes = new ConnNode[2];
    /**
     * 连接的状态
     */
    private String status;

}
