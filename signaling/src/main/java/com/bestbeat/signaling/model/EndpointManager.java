package com.bestbeat.signaling.model;

import com.bestbeat.signaling.util.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket节点管理
 * 单例模式-饿汉模式
 * @author zhangqq
 */
@Data
@Slf4j
public class EndpointManager {

    private static EndpointManager endpointManager = new EndpointManager();

    private EndpointManager(){

    }

    public static EndpointManager getInstance(){
        return endpointManager;
    }


    /**
     * 房间表
     */
    private Map<String,Set<Endpoint>> rooms = new HashMap<>();


    /**
     * 根据房间号发送消息
     * @param o_message
     */
    public synchronized void send(Map<String,Object> o_message) {

        String roomId = (String) o_message.get("roomId");
        ObjectMapper jsonObject = new ObjectMapper();
        Set<Endpoint> room = rooms.get(roomId);
        for (Endpoint peer : room) {
            try {
                if (peer.getUuid().toString().equals(o_message.get("to"))) {
                    peer.getSession().getAsyncRemote().sendText(jsonObject.writeValueAsString(o_message));
                    break;
                } else if ("all".equals(o_message.get("to"))) {

                    peer.getSession().getAsyncRemote().sendText(jsonObject.writeValueAsString(o_message));

                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭连接
     * @param endpoint
     */
    public synchronized void close(Endpoint endpoint,Map<String,Object> o_message){

        ObjectMapper jsonObject = new ObjectMapper();
        String roomId = endpoint.getRoomId();
        o_message.put("from",endpoint.getUuid().toString());

        if (StringUtils.isEmpty(roomId)) {
            log.info("The roomId can not be empty");
            return;
        }

        if (!this.rooms.containsKey(roomId)) {
            log.info("Not Found roomId ");
            return;
        }

        Set<Endpoint> room = this.rooms.get(roomId);
        if (room == null) {
            this.rooms.remove(roomId);
        } else {

            room.remove(endpoint);

            for (Endpoint peer : room) {
                try {
                    peer.getSession().getAsyncRemote().sendText(jsonObject.writeValueAsString(o_message));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 连接信令服务器
     * 并加入房间
     * @param endpoint
     */
    public synchronized void connect(Endpoint endpoint) {

        if (StringUtils.isEmpty(endpoint.getRoomId())) {
            log.info("The roomId can not be empty");
            return;
        }

        if (!this.rooms.containsKey(endpoint.getRoomId())) {
            Set<Endpoint> room = new CopyOnWriteArraySet<>();
            this.rooms.put(endpoint.getRoomId(),room);
        }

        Set<Endpoint> room = this.rooms.get(endpoint.getRoomId());
        Map<String,Object> sendContent = new HashMap<>();
        ObjectMapper jsonObject = new ObjectMapper();
        if (room.size() > 4) {
            sendContent.put("type", MessageType.SIGNALING_CONNECT_ERROR);
            sendContent.put("from",endpoint.getUuid().toString());
            sendContent.put("to",endpoint.getUuid().toString());
            sendContent.put("roomId",endpoint.getRoomId());
            sendContent.put("content","连接数量已达最大");
            try {
                endpoint.getSession().getAsyncRemote().sendText(jsonObject.writeValueAsString(sendContent));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return;
        }
        room.add(endpoint);

        log.debug("已经加入{}房间",endpoint.getRoomId());

        sendContent.put("type", MessageType.SIGNALING_CONNECT.name());
        sendContent.put("from",endpoint.getUuid().toString());
        sendContent.put("to","all");
        sendContent.put("roomId",endpoint.getRoomId());
        sendContent.put("content",null);

        for (Endpoint peer : room) {

            try {
                peer.getSession().getAsyncRemote().sendText(jsonObject.writeValueAsString(sendContent));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }

    }

}
