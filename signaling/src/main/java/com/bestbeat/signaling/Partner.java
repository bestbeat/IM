package com.bestbeat.signaling;

import java.util.HashMap;
import java.util.Map;

/**
 * 连接节点在连接上的对应映射
 * @author zhangqq
 */
public class Partner {

    /**
     * 节点映射表
     */
    private Map<String,ConnNode> nodeTable = new HashMap<>();

    /**
     * 根据提供节点，删除对应映射
     * @param thisNode
     * @return
     */
    public ConnNode delConnNodeMap(ConnNode thisNode){
       return nodeTable.remove(thisNode.getId());
    }

    /**
     * 根据提供节点id，找到连接上对应的另一个节点
     * @param id
     * @return
     */
    public ConnNode getOtherNodeById(String id){
        return  nodeTable.get(id);
    }

    /**
     * 根据提供节点，找到连接上对应的另一个节点
     * @param thisNode
     * @return
     */
    public ConnNode getOtherNode(ConnNode thisNode){
        return  getOtherNodeById(thisNode.getId());
    }

    /**
     *  根据连接上的两节点，添加映射
     * @param firstNode
     * @param secondNode
     */
    public void addConnNodeMap(ConnNode firstNode,ConnNode secondNode){
        nodeTable.put(firstNode.getId(),secondNode);
        nodeTable.put(secondNode.getId(),firstNode);
    }

}
