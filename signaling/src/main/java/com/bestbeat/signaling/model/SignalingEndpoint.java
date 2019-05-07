package com.bestbeat.signaling.model;

import com.bestbeat.signaling.util.ConnConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 信令通道
 * @author zhangqq
 */
@ServerEndpoint("/signaling/{key}")
@Component
@Slf4j
public class SignalingEndpoint {

    private static CopyOnWriteArraySet<SignalingEndpoint> signalingEndpoints = new CopyOnWriteArraySet<>();

    /**
     * 连接表
     */
    private Map<String,Connection> conns = new HashMap<>();

    /**
     * 连接节点映射表
     */
    private Partner partner = new Partner();

    /**
     * 消息发送存储
     */
    private Map<String, Queue<String>> messageFor = new HashMap<>();

    @OnClose
    public void closeConn(){

    }

    /**
     * 发送客户端消息
     * @param message
     */
    @OnMessage
    public void onMessage(String message){
        sendMessage(message);
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(String message){
//        this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 连接成功处理方法
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {
        signalingEndpoints.add(this);
        log.info("{}",signalingEndpoints.size());
        log.info("连接正在建立...");
        if(StringUtils.isEmpty(key)){
            session.getAsyncRemote().sendText("No recognized query key");
        }else{
            Connection conn = this.conns.get(key);
            if(conn ==null){
                conn = new Connection();
                conn.setStatus(ConnConstant.CONNECTION_NEW.constantVal);
            }
            if(conn.getStatus().equals(ConnConstant.CONNECTION_WAIT.constantVal)){
                ConnNode secondNode = new ConnNode(createConnID());
                secondNode.setSession(session);
                ConnNode firstNode = conn.getNodes().get(0);
                conn.getNodes().add(1,secondNode);
                partner.addConnNodeMap(firstNode,secondNode);
                messageFor.put(firstNode.getId(),new ArrayBlockingQueue<String>(10));
                messageFor.put(secondNode.getId(),new ArrayBlockingQueue<String>(10));
                conn.setStatus(ConnConstant.CONNECTION_SUCCESS.constantVal);
                Map<String,String> ret = new HashMap<>();
                ret.put("id",secondNode.getId());
                ret.put("status",conn.getStatus());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    session.getAsyncRemote().sendText(mapper.writeValueAsString(ret));
                    log.info("连接建立成功，另一方已加入...");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }else{
                if(conn.getStatus().equals(ConnConstant.CONNECTION_SUCCESS.constantVal)){
                    this.partner.delConnNodeMap(conn.getNodes().get(0));
                    this.partner.delConnNodeMap(conn.getNodes().get(1));
                    this.messageFor.remove(conn.getNodes().get(0));
                    this.messageFor.remove(conn.getNodes().get(1));
                }
                conn = new Connection();
                this.conns.put(key,conn);
                conn.setStatus(ConnConstant.CONNECTION_WAIT.constantVal);
                ConnNode firstNode = new ConnNode(createConnID());
                firstNode.setSession(session);
                conn.getNodes().add(0, firstNode);
                Map<String,String> ret = new HashMap<>();
                ret.put("id",conn.getNodes().get(0).getId());
                ret.put("status",conn.getStatus());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    session.getAsyncRemote().sendText(mapper.writeValueAsString(ret));
                    log.info("连接建立成功，等待另一个加入...");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private  String createConnID() {
        return String.valueOf(Math.floor(Math.random()*1000000000));
    }
}
