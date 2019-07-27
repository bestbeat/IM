package com.bestbeat.signaling.model;

import com.bestbeat.signaling.util.MessageType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 信令通道
 * @author zhangqq
 */
@ServerEndpoint("/signaling/{roomId}")
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

    /**
     * UUID
     */
    private UUID uuid;

    /**
     * 房间号，每个用户只能进入一个房间
     */
    private String roomId;

    @OnError
    public  void onError(Throwable e){
        log.info(e.getMessage());
    }

    @OnClose
    public void onClose(){
        log.info("websocket close");
        Map<String,Object> msg = new HashMap<>();
        msg.put("type",MessageType.SIGNALING_CLOSE.name());
        msg.put("from",this.getId());
        msg.put("to","all");
        msg.put("roomId",this.getRoomId());
        msg.put("content","");
        EndpointManager.getInstance().close(this,msg);
    }

    /**
     * 接收客户端消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        ObjectMapper jsonObject = new ObjectMapper();
        EndpointManager manager = EndpointManager.getInstance();
        if (StringUtils.isEmpty(message)) {
            log.info("The message is null");
            return;
        }
        try {
            Map<String,Object> msg= jsonObject.readValue(message,Map.class);
            String type = (String) msg.get("type");
            if (MessageType.HEART_BEAT.name().equals(type)) {

            } else if (MessageType.SIGNALING_CLOSE.name().equals(type)) {
                manager.close(this,msg);
            } else {
                manager.send(msg);
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
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        this.session=session;
        this.roomId = roomId;
        this.uuid = UUID.randomUUID();
        EndpointManager.getInstance().connect(this);
    }

}
