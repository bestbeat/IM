package com.bestbeat.signaling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

/**
 * 信令通道
 * @author zhangqq
 */
@ServerEndpoint("/signaling/{key}")
@Component
@Slf4j
public class SignalingChannel {

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
     * @param session
     */
    @OnMessage
    public void sendMessage(Session session,String message){

        session.getAsyncRemote().sendText(message);

    }

    /**
     * 连接成功处理方法
     * @param session
     */
    @OnOpen
    public void connect(Session session, @PathParam("key") String key) {
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
                ConnNode firstNode = conn.getNodes()[0];
                conn.getNodes()[1] = secondNode;
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
                    this.partner.delConnNodeMap(conn.getNodes()[0]);
                    this.partner.delConnNodeMap(conn.getNodes()[1]);
                    this.messageFor.remove(conn.getNodes()[0]);
                    this.messageFor.remove(conn.getNodes()[1]);
                }
                conn = new Connection();
                this.conns.put(key,conn);
                conn.setStatus(ConnConstant.CONNECTION_WAIT.constantVal);
                conn.getNodes()[0] =  new ConnNode(createConnID());
                Map<String,String> ret = new HashMap<>();
                ret.put("id",conn.getNodes()[0].getId());
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
