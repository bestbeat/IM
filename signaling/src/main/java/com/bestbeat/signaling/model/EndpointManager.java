package com.bestbeat.signaling.model;

import com.bestbeat.signaling.util.ConnConstant;
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
            if (conn.getStatus().equals(ConnConstant.CONNECTION_SUCCESS.constantVal)) {
                Iterator<Endpoint> it = conn.getNodes().iterator();
                while (it.hasNext()){
                    Endpoint p = it.next();
                    if (p.getId().equals(endpoint.getId())) {
                        p.getSession().getAsyncRemote().sendText("{status:"+ ConnConstant.CONNECTION_WAIT.constantVal + "}");
                        it.remove();
                        conn.setStatus(ConnConstant.CONNECTION_WAIT.constantVal);
                        break;
                    }
                }
            } else {
                this.conns.get(secretKey).getNodes().get(0).getSession().getAsyncRemote().sendText("{status:closed}");
                this.conns.remove(secretKey);
            }
        }

    }

    public synchronized void connect(Endpoint endpoint,String secretKey){
        if (StringUtils.isEmpty(secretKey)) {
            throw new RuntimeException("The secretKey can not be empty");
        }
        Connection conn = this.conns.get(secretKey);
        if (conn == null) {
            conn = new Connection();
            conn.setConnKey(secretKey);
            conn.setStatus(ConnConstant.CONNECTION_NEW.constantVal);
            this.conns.put(secretKey,conn);
        }

        if (conn.getStatus().equals(ConnConstant.CONNECTION_WAIT.constantVal)) {
            partner.addConnNodeMap(conn.getNodes().get(0),endpoint);
            conn.setStatus(ConnConstant.CONNECTION_SUCCESS.constantVal);
            for (Endpoint p : conn.getNodes()) {
                p.getSession().getAsyncRemote().sendText("{status:"+ ConnConstant.CONNECTION_SUCCESS.constantVal + "}");
            }
        } else {
            if (conn.getStatus().equals(ConnConstant.CONNECTION_SUCCESS.constantVal)) {
                this.partner.delConnNodeMap(endpoint);
            }
            conn.getNodes().add(endpoint);
            conn.setStatus(ConnConstant.CONNECTION_WAIT.constantVal);
            endpoint.getSession().getAsyncRemote().sendText("{status:"+ ConnConstant.CONNECTION_WAIT.constantVal + "}");
        }

    }

    /**
     * 与WebSocket 服务器建立连接,注册
     * @param endpoint
     */
    public synchronized void register(Endpoint endpoint){
        endpoint.setId(createConnID());
        endpoints.add(endpoint);
        endpoint.getSession().getAsyncRemote().sendText("{id: "+ endpoint.getId() + "}");
        log.info("浏览器与服务器建立连接完成，节点ID: {}",endpoint.getId());
    }

    /**
     * 获取新节点ID
     * @return
     */
    private  String createConnID() {
        return String.valueOf(Math.floor(Math.random()*1000000000));
    }
}
