package com.xumingxing.webpack.userfunction.enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: 徐明星  --写代码的徐师傅
 * Date: 2019/7/2915:07
 * Description: Version 1.0
 * Location: webpack
 */
public enum ResponseStatus {

    /**
     * 成功 无响应数据
     */
    SUCCESS_NONE_DATA(1000, "成功"),
    /**
     * 成功 有响应数据
     */
    SUCCESS(1001, "成功");


    private int status;
    private String message;

    ResponseStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}


