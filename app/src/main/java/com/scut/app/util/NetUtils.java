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

    /**
     * 网关
     */
    private static final String IP_PRE = "http://218.192.160.28:10010/";
    public static final String TOKEN_HEADER = "access-token";

    public static final String CLIENT_HEADER = "client-key";

    public static final String SECRET_HEADER = "client-secret";

    /**
     * 获取一个基本的 Retrofit.Builder
     */
    private static Retrofit.Builder getRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain.request();
                Request build = request.newBuilder()
                        .addHeader(CLIENT_HEADER,
                                MyApplication.getInstance().getSecret().getClientKey()).build();
                return chain.proceed(build);
            }
        });

        return new Retrofit.Builder()
                .baseUrl(IP_PRE)
                .client(builder.build())
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
