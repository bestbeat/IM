package com.bestbeat.signaling.util;

/**
 * 连接常量
 * @author zhangqq
 */
public enum ConnConstant {

    /**
     * 连接等待
     */
    CONNECTION_WAIT("waiting"),
    /**
     * 连接成功
     */
    CONNECTION_SUCCESS("connected"),
    /**
     * 新建连接
     */
    CONNECTION_NEW("new")
    ;

    public String constantVal;
    ConnConstant(String constantVal) {
        this.constantVal = constantVal;
    }


}
