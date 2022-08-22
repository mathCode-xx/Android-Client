package com.scut.app.net;

import com.scut.app.entity.LoginBean;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;

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

    /**
     * 登录接口
     *
     * @param loginBean 登录学号、密码
     * @return 异步操作对象
     */
    @POST("user/login")
    Observable<ResponseData> login(@Body LoginBean loginBean);

    /**
     * 注册
     * @param user
     * @return
     */
    @POST("user/register")
    Observable<ResponseData> register(@Body User user);

    @GET("user/audit")
    Single<ResponseData> getNeedAudit(@Header("token") String token);

    @PUT("user/audit/success")
    Single<ResponseData> commitAudit(@Body List<String> ids, @Header("token") String token);
}
