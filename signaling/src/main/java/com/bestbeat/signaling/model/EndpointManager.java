package com.bestbeat.signaling.model;

import com.bestbeat.signaling.util.ConnConstant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
     * 节点注册表
     */
    private CopyOnWriteArraySet<Endpoint> endpoints = new CopyOnWriteArraySet<>();

    /**
     * 连接表
     */
    private Map<String,Connection> conns = new HashMap<>();

    /**
     * 连接节点映射表
     */
    private Partner partner = new Partner();

    public synchronized void send(Endpoint endpoint,String msg) {
        Endpoint other = this.partner.getOtherNode(endpoint);
        other.getSession().getAsyncRemote().sendText(msg);
        endpoint.getSession().getAsyncRemote().sendText(msg);
    }

    /**
     * 关闭连接
     * @param endpoint
     */
    public synchronized void close(Endpoint endpoint,String secretKey){

        this.partner.delConnNodeMap(endpoint);

        if (StringUtils.isEmpty(secretKey)) {
            throw new RuntimeException("The secretKey can not be empty");
        }

        if (!this.conns.containsKey(secretKey)) {
            throw new RuntimeException("Not Found Connetion of the secretKey mapper");
        }

        Connection conn = this.conns.get(secretKey);
        if (conn == null) {
            this.conns.remove(secretKey);
        } else {
            Map<String,Object> sendMap = new HashMap<>();
            ObjectMapper objectMapper = new ObjectMapper();
            if (conn.getStatus().equals(ConnConstant.CONN_SUCCESS.constantVal)) {
                Iterator<Endpoint> it = conn.getNodes().iterator();
                while (it.hasNext()){
                    Endpoint p = it.next();
                    if (p.getId().equals(endpoint.getId())) {
                        sendMap.put("method","close");
                        sendMap.put("status",ConnConstant.CONN_WAIT.constantVal);
                        try {
                            p.getSession().getAsyncRemote().sendText(objectMapper.writeValueAsString(sendMap));
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        it.remove();
                        conn.setStatus(ConnConstant.CONN_WAIT.constantVal);
                        break;
                    }
                }
            } else {
                sendMap.put("method","close");
                sendMap.put("status",ConnConstant.CONN_CLOSE.constantVal);
                try {
                    this.conns.get(secretKey).getNodes().get(0).getSession().getAsyncRemote().sendText(objectMapper.writeValueAsString(sendMap));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                this.conns.remove(secretKey);
            }
        }

    }

    public synchronized void connect(Endpoint endpoint,String secretKey) throws JsonProcessingException {

        if (StringUtils.isEmpty(secretKey)) {
            throw new RuntimeException("The secretKey can not be empty");
        }
        Connection conn = this.conns.get(secretKey);
        if (conn == null) {
            conn = new Connection();
            conn.setConnKey(secretKey);
            conn.setStatus(ConnConstant.CONN_NEW.constantVal);
            this.conns.put(secretKey,conn);
        }

        Map<String,Object> sendMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (conn.getStatus().equals(ConnConstant.CONN_WAIT.constantVal)) {
            conn.getNodes().add(endpoint);
            partner.addConnNodeMap(conn.getNodes().get(0),endpoint);
            conn.setStatus(ConnConstant.CONN_SUCCESS.constantVal);
            sendMap.put("method","connect");
            sendMap.put("status",ConnConstant.CONN_SUCCESS.constantVal);
            for (Endpoint p : conn.getNodes()) {
                p.getSession().getAsyncRemote().sendText(objectMapper.writeValueAsString(sendMap));
            }
        } else {
            if (conn.getStatus().equals(ConnConstant.CONN_SUCCESS.constantVal)) {
                this.partner.delConnNodeMap(endpoint);
            }
            conn.getNodes().add(endpoint);
            conn.setStatus(ConnConstant.CONN_WAIT.constantVal);
            sendMap.put("method","connect");
            sendMap.put("status",ConnConstant.CONN_WAIT.constantVal);
            try {
                endpoint.getSession().getAsyncRemote().sendText(objectMapper.writeValueAsString(sendMap));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 与WebSocket 服务器建立连接,注册
     * @param endpoint
     */
    public synchronized void register(Endpoint endpoint){
        endpoint.setId(createConnID());
        endpoints.add(endpoint);
        Map<String,Object> sendMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        sendMap.put("method","register");
        sendMap.put("id",endpoint.getId());
        try {
            endpoint.getSession().getAsyncRemote().sendText(objectMapper.writeValueAsString(sendMap));
            log.info("浏览器与服务器建立连接完成，节点ID: {}",endpoint.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取新节点ID
     * @return
     */
    private  String createConnID() {
        return String.valueOf(Math.floor(Math.random()*1000000000));
    }
}
