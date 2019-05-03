package com.bestbeat.signaling;

/**
 * 连接常量
 */
public enum ConnectionConstant {

    /**
     * 连接等待
     */
    CONNECTION_WAIT(1),
    /**
     * 连接成功
     */
    CONNECTION_SUCCESS(2),
    /**
     * 多人连接
     */
    CONNECTION_MULTI(3)
    ;

    public int constatnIndex;
    ConnectionConstant(int constatnIndex) {
        this.constatnIndex = constatnIndex;
    }


}
