package com.scut.app.net;

import com.scut.app.entity.ResponseData;

/**
 * 用于网络请求之后的回调接口
 * @author 徐鑫
 */
public interface CallBack {
    /**
     * 请求成功
     * @param responseData 所需数据
     */
    void success(ResponseData responseData);

    /**
     * 请求失败
     * @param message 失败信息
     */
    void fail(String message);
}
