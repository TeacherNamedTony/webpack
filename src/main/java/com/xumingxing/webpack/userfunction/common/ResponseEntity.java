package com.xumingxing.webpack.userfunction.common;

import com.alibaba.fastjson.JSON;
import com.xumingxing.webpack.userfunction.enumeration.ResponseStatus;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2915:05
 * Description: Version 1.0
 * Location: webpack
 */
public class ResponseEntity<T> {

    /**
     * 状态码
     */
    private int status;
    /**
     * 消息
     */
    private String message;
    /**
     * 响应数据集
     */
    private T data;

    /*
     * QuoteFieldNames 输出key时是否使用双引号,默认为true
     *
     * WriteMapNullValue 是否输出值为null的字段,默认为false
     *
     * WriteNullNumberAsZero 数值字段如果为null,输出为0,而非null
     *
     * WriteNullListAsEmpty List字段如果为null,输出为[],而非null
     *
     * WriteNullStringAsEmpty 字符类型字段如果为null,输出为"",而非null
     *
     * WriteNullBooleanAsFalse Boolean字段如果为null,输出为false,而非null
     *
     * SerializerFeature
     * .WRITE_MAP_NULL_FEATURES
     */

    /**
     * 使用枚举类型构造响应结果
     * 无响应数据集
     * @param responseStatus
     * @return
     */
    public static String responseToJSONStringNoneData(ResponseStatus responseStatus) {
        return JSON.toJSONString(new ResponseEntity<>(responseStatus.getStatus(),responseStatus.getMessage()));
    }

    /**
     * 使用枚举类型构造响应结果
     * 有响应数据集
     * @param responseStatus
     * @param o
     * @return
     */
    public static String responseToJSONStringWithData(ResponseStatus responseStatus,Object o) {
        return JSON.toJSONString(new ResponseEntity<>(responseStatus.getStatus(),responseStatus.getMessage(),o));
    }

    /**
     * 自定义响应结果
     * 无响应数据集
     * @param status
     * @param message
     * @return
     */
    public static String responseToJSONStringNoneData(int status,String message) {
        return JSON.toJSONString(new ResponseEntity<>(status,message));
    }

    /**
     * 自定义响应结果
     * 无响应数据集
     * @param status
     * @param message
     * @param o
     * @return
     */
    public static String responseToJSONStringWithData(int status,String message,Object o) {
        return JSON.toJSONString(new ResponseEntity<>(status,message,o));
    }

    private ResponseEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    private ResponseEntity(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
