package com.scut.app;

import android.app.Application;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.json.JSONUtil;

/**
 * 重写application, 用于保存全局变量
 * @author 徐鑫
 */
public class MyApplication extends Application {

    public static final String USER_KEY = "user";
    public static final String TOKEN_KEY = "token";

    private static MyApplication instance;

    private final Map<String, String> map = new HashMap<>();

    public static MyApplication getInstance() {
        return instance;
    }

    public void put(String key, String data) {
        map.put(key, data);
    }

    public void putObj(String key, Object obj) {
        this.put(key, JSONUtil.toJsonStr(obj));
    }

    public String getStr(String key) {
        return this.map.get(key);
    }

    public <T> T getObj(String key, Class<T> tClass) {
        return JSONUtil.toBean(getStr(key), tClass);
    }

    public boolean haveLogin() {
        return this.map.get(USER_KEY) != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
