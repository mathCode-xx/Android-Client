package com.scut.bbs.util;

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

    public static <T> T createRetrofit(Class<T> tClass) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(IP_PRE)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return build.create(tClass);
    }

}
