package com.scut.app.util;

import androidx.annotation.NonNull;

import com.scut.app.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * 封装的okhttp工具
 *
 * @author 徐鑫
 */
public class NetUtils {

    private static final String IP_PRE = "http://218.192.160.28:8080/";

    /**
     * 获取一个基本的 Retrofit.Builder
     */
    private static Retrofit.Builder getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(IP_PRE)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create());
    }

    /**
     * 没有加请求头
     */
    public static <T> T createRetrofit(Class<T> tClass) {
        return getRetrofit().build().create(tClass);
    }
}
