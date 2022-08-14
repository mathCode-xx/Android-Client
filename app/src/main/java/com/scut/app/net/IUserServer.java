package com.scut.app.net;

import com.scut.app.entity.LoginBean;
import com.scut.app.entity.ResponseData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 与user有关的接口
 *
 * @author 徐鑫
 */
public interface IUserServer {

    /**
     * @param loginBean
     * @return
     */
    @POST("user/login")
    Observable<ResponseData> login(@Body LoginBean loginBean);
}
