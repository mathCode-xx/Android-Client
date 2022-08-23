package com.scut.app.entity;

import java.util.Map;

/**
 * 与前端交互的数据实体
 *
 * @author 徐鑫
 */
public class ResponseData {
    private int statusCode;
    private String message;
    private Map<String, Object> data;

    public final static int SUCCESS_CODE = 200;
    public final static int FAIL_CODE = 500;

    static public ResponseData success() {
        ResponseData m = new ResponseData();
        m.setMessage("请求成功");
        m.setStatusCode(200);
        return m;
    }

    static public ResponseData success(String message) {
        ResponseData m = new ResponseData();
        m.setStatusCode(200);
        m.setMessage(message);
        return m;
    }

    static public ResponseData fail() {
        ResponseData responseData = new ResponseData();
        responseData.setMessage("请求失败");
        responseData.setStatusCode(500);
        return responseData;
    }

    static public ResponseData fail(String message) {
        ResponseData responseData = new ResponseData();
        responseData.setMessage(message);
        responseData.setStatusCode(500);
        return responseData;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
