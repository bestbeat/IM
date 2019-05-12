package com.bestbeat.signaling.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * 信令通道
 * @author zhangqq
 */
@ServerEndpoint("/signaling")
@Component
@Slf4j
@Data
public class Endpoint {

    /**
     * 节点ID
     */
    private String Id;
    /**
     * 与websocket服务器会话对象
     */
    private Session session;

    @OnError
    public  void onError(Throwable e){

    }

    @OnClose
    public void onClose(){
        log.info("999");
        EndpointManager.getInstance().getEndpoints().remove(this);
    }

    /**
     * 接收客户端消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        ObjectMapper om = new ObjectMapper();
        EndpointManager manager = EndpointManager.getInstance();
        try {
            Map<String,String> msg= om.readValue(message,Map.class);
            String method = msg.get("method");
            String secretKey = msg.get("secretKey");
            String content = msg.get("content");
            switch (method) {
                case "connect": manager.connect(this,secretKey);break;
                case "close": manager.close(this,secretKey);break;
                default:manager.send(this,content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接成功处理方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session=session;
        EndpointManager.getInstance().register(this);
    }

}
