package com.scut.app.net;

import com.scut.app.entity.LoginBean;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.key.entity.Secret;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * 与user有关的接口
 *
 * @author 徐鑫
 */
public interface IUserServer {

    @GET("user/pub")
    Single<ResponseData> requirePubKey();

    @POST("user/init")
    Single<ResponseData> init(@Body Secret secret);

    @POST("user/login")
    Observable<ResponseData> login(@Body LoginBean loginBean);

    @POST("user/register")
    Observable<ResponseData> register(@Body User user);

    @GET("user/audit/need")
    Single<ResponseData> getNeedAudit(@Header("token") String token);

    @PUT("user/audit/commit")
    Single<ResponseData> commitAudit(@Body List<String> ids, @Header("token") String token);

    @GET("user/audit/finish")
    Single<ResponseData> getFinishAudit(@Header("token") String token);

    @PUT("user/audit/rollback")
    Single<ResponseData> rollbackAudit(@Body List<String> ids, @Header("token") String token);
}
