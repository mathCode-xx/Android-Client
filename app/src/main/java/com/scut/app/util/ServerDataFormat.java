package com.scut.app.util;

import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 将服务端数据格式化
 *
 * @author 徐鑫
 */
public class ServerDataFormat {

    private final JSONObject data;

    public ServerDataFormat(String data) {
        this.data = JSONUtil.parseObj(data);
    }

    /**
     * 获取响应码
     *
     * @return 响应码
     */
    public Integer getCode() {
        return Integer.valueOf(this.data.getStr("statusCode"));
    }

    /**
     * 获取响应信息
     *
     * @return 信息
     */
    public String getMsg() {
        return this.data.getStr("message");
    }

    /**
     * 获取data数据中携带的类
     *
     * @param tClass class对象
     * @param <T>    类类型
     * @return 获取到的类
     */
    public <T> T getDataObj(Class<T> tClass) {
        JSONObject data = this.data.getJSONObject("data");
        return JSONUtil.toBean(data, tClass);
    }

    /**
     * 获取data数据中携带的集合
     *
     * @param tClass class对象
     * @param <T>    集合类型
     * @return 获取到的集合
     */
    public <T> List<T> getDataList(Class<T> tClass) {
        JSONArray data = this.data.getJSONArray("data");
        return JSONUtil.toList(data, tClass);
    }

    /**
     * 获取"data"中的字符串
     *
     * @return 获取到的字符串
     */
    public String getData() {
        return data.toString();
    }

    /**
     * 根据key获取"data"中的字符串
     *
     * @param key 键
     * @return 获取到的字符串
     */
    public String getDataStrWithKey(String key) {
        return this.data.getJSONObject("data").getStr(key);
    }

    /**
     * 根据key获取"data"中的对象
     *
     * @param key 键
     * @return 获取到的对象
     */
    public <T> T getDataObjWithKey(String key, Class<T> tClass) {
        JSONObject data = this.data.getJSONObject("data").getJSONObject(key);
        return JSONUtil.toBean(data, tClass);
    }

    /**
     * 根据key获取"data"中的集合
     *
     * @param key 键
     * @return 获取到的集合
     */
    public <T> List<T> getDataListWithKey(String key, Class<T> tClass) {
        JSONArray data = this.data.getJSONObject("data").getJSONArray(key);
        return JSONUtil.toList(data, tClass);
    }
}
