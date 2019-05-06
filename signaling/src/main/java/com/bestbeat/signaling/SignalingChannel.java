package com.bestbeat.signaling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    /**
     * 连接会话
     */
//    private Session session;


    /**
     * 发送客户端消息
     * @param message
     * @param session
     */
    @OnMessage
    public void sendMessage(String message,Session session){
        Map<String,String> params = session.getPathParameters();
        this.messageFor.get(params.get("id")).add(message);
        session.getAsyncRemote().sendText("Saving message *** "+ message + "*** for delivery to id " + this.partner.getOtherNodeById(params.get("id")));
    }

    /**
     * 连接成功处理方法
     * @param session
     */
    @OnOpen
    public void connect(Session session, @PathParam("key") String key) {
//        this.session = session;
        log.info("ddddd");
        System.out.println("sss");
        Map<String,String> params = session.getPathParameters();
        if(params==null || params.get("key").isEmpty()){
            Connection conn = this.conns.get(params.get("key"));
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
                this.conns.put(params.get("key"),conn);
                conn.setStatus(ConnConstant.CONNECTION_WAIT.constantVal);
                conn.getNodes()[0] =  new ConnNode(createConnID());
                Map<String,String> ret = new HashMap<>();
                ret.put("id",conn.getNodes()[0].getId());
                ret.put("status",conn.getStatus());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    session.getAsyncRemote().sendText(mapper.writeValueAsString(ret));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }else{
            session.getAsyncRemote().sendText("No recognized query key");
        }
    }

    private  String createConnID() {
        return String.valueOf(Math.floor(Math.random()*1000000000));
    }
}
