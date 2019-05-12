package com.bestbeat.signaling.util;

/**
 * 连接常量
 * @author zhangqq
 */
public enum ConnConstant {

    /**
     * 连接关闭
     */
    CONN_CLOSE("close"),
    /**
     * 连接等待
     */
    CONN_WAIT("waiting"),
    /**
     * 连接成功
     */
    CONN_SUCCESS("connected"),
    /**
     * 新建连接
     */
    CONN_NEW("new")
    ;

    public String constantVal;
    ConnConstant(String constantVal) {
        this.constantVal = constantVal;
    }


}
