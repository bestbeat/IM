package com.bestbeat.signaling;

import lombok.Data;

/**
 * 连接节点
 * @author zhangqq
 */
@Data
public class ConnNode {

    public ConnNode(){};
    public ConnNode(String id){
        this.id = id;
    }

    /**
     * 节点连接ID
     */
    private String id;
    /**
     * 节点目前状态
     */
    private String status;
}
