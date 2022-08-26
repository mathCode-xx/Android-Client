package com.scut.bbs.net;

import com.scut.bbs.entity.ResponseData;
import com.scut.bbs.login.bean.LoginBean;

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
