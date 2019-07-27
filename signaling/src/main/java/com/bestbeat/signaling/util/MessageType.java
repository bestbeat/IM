package com.bestbeat.signaling.util;

/**
 * @author 张渠钦
 * 2019/06/20
 * 消息类型枚举类
 */
public enum MessageType {

//    信道连接
    SIGNALING_CONNECT,
//    信道发送
    SIGNALING_SEND,
//    信道关闭
    SIGNALING_CLOSE,
//    webrtc 提议
    WEBRTC_OFFER,
//    webrtc 应答
    WEBRTC_ANSWER,
//    webrtc ice传输
    WEBRTC_ICE,
//    心跳包
    HEART_BEAT,
//    信令连接错误
    SIGNALING_CONNECT_ERROR

}
