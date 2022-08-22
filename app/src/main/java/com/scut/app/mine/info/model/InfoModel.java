package com.scut.app.mine.info.model;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.mine.info.vm.MyBbsViewModel;
import com.scut.app.net.CallBack;
import com.scut.app.net.ITopicServer;
import com.scut.app.util.NetUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class InfoModel {
    private static final String TAG = "InfoModel";

    public void requireMyTopic(CallBack callBack) {
        ITopicServer retrofit = NetUtils.createRetrofit(ITopicServer.class);
        User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);
        Log.d(TAG, "requireMyTopic: " + user.id);
        Single<ResponseData> single = retrofit.getTopicByUserId(user.id);
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

    public void deleteTopic(Topic topic, CallBack callBack) {
        ITopicServer retrofit = NetUtils.createRetrofit(ITopicServer.class);
        Single<ResponseData> single = retrofit.delete(topic, MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY));
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
