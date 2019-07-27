package com.bestbeat.web.error;

/**
 * @author 张渠钦
 * 2019/6/19
 * 数据范围超出异常
 */
public class DataRangeOutException extends RuntimeException {

    public DataRangeOutException(){
        super();
    }

    public DataRangeOutException(String eMessage) {
        super(eMessage);
    }

}
