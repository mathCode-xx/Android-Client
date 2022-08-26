package com.scut.app.net;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.entity.ResponseData;
import com.scut.app.util.NetUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LogServer {


    public void getAllLogs(CallBack callBack) {
        ILogServer retrofit = NetUtils.createRetrofit(ILogServer.class);
        String token = MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY);
        Single<ResponseData> single =
                retrofit.getAllLogs(token);
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getStatusCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                            return;
                        }
                        callBack.fail(responseData.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callBack.fail(e.getMessage());
                    }
                });

    }

}
