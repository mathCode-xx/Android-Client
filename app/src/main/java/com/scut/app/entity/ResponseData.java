package com.scut.app.entity;

import java.util.Map;

/**
 * 与前端交互的数据实体
 *
 * @author 徐鑫
 */
public class ResponseData {
    private int errCode;
    private String errMessage;
    private Map<String, Object> data;

    public final static int SUCCESS_CODE = 0;
    public final static int FAIL_CODE = 500;

    public int getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
