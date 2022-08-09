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

    private static MyApplication instance;

    private Map<String, String> map = new HashMap<>();

    public static MyApplication getInstance() {
        return instance;
    }

    public void put(String key, String data) {
        map.put(key, data);
    }

    public void putObj(String key, Object obj) {
        this.put(key, JSONUtil.toJsonStr(obj));
    }



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
