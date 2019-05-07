package com.bestbeat.signaling.model;

import lombok.Data;

import javax.websocket.Session;

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
     * ip4地址
     */
    private String ipv4;
    /**
     * ip6地址
     */
    private String ipv6;
    /**
     * websocket session
     */
    private Session session;

    /**
     * 节点目前状态
     */
    private String status;
}
