package com.scut.app.net;

import com.scut.app.entity.ResponseData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ILogServer {

    @GET("/log")
    Single<ResponseData> getAllLogs(@Header("token") String token);
}
