package com.bestbeat.web.utils;

import com.bestbeat.web.error.DataRangeOutException;

import java.util.Date;

/**
 * @author 张渠钦
 * 2019/6/19
 * 唯一标识编码生成工具类
 */
public class IdUtils {

    /**
     * 用户id 序列当前索引值
     */
    private static int userIndex;

    /**
     * 创建userId;
     * @return userId
     */
    public static String createUserId(){

        if(userIndex == Integer.MAX_VALUE){
            throw new DataRangeOutException(" 用户id 序列 超过最大值，不可生成 ");
        }
        userIndex++ ;
        return String.valueOf(userIndex);
    }

    /**
     * 创建roomId
     * @return
     */
    public static String createRoomId() {
        int randomValue = (int) Math.round(32 * Math.random());
        int datehash =  new Date().hashCode();
        int returnValue = (datehash << randomValue) ^ randomValue;
        return String.valueOf(returnValue);
    }

}
