package com.scut.app.net;

import com.scut.app.MyApplication;
import com.scut.app.entity.ResponseData;
import com.scut.app.util.NetUtils;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserServer {
    IUserServer retrofit = NetUtils.createRetrofit(IUserServer.class);

    public void getNeedAudit(CallBack callBack) {
        Single<ResponseData> single = retrofit.getNeedAudit(MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY));
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

    public void commitAudit(List<String> ids, CallBack callBack) {
        String token = MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY);
        Single<ResponseData> single = retrofit.commitAudit(ids, token);

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

    public void getFinishAudit(CallBack callBack) {
        Single<ResponseData> single = retrofit.getFinishAudit(MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY));
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

    public void commitRollback(List<String> ids, CallBack callBack) {
        String token = MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY);
        Single<ResponseData> single = retrofit.rollbackAudit(ids, token);

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
