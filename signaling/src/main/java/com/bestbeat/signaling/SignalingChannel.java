package com.bestbeat.signaling;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;

/**
 * 信令通道
 */
@ServerEndpoint("/signaling")
public class SignalingChannel {

    /**
     * 连接成功处理方法
     * @param session
     */
    @OnOpen
    public void connection(Session session) {
        Map<String,String> params = session.getPathParameters();

    }

    private static String createConnID() {
        return String.valueOf(Math.floor(Math.random()*1000000000));
    }
}
