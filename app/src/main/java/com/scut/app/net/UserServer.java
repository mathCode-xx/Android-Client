package com.scut.app.net;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.entity.ResponseData;
import com.scut.app.key.asymmetry.AsymmetryKey;
import com.scut.app.key.entity.Secret;
import com.scut.app.util.NetUtils;
import com.scut.app.util.ToastUtils;

import java.util.List;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserServer {
    private static final String TAG = "UserServer";
    IUserServer retrofit = NetUtils.createRetrofit(IUserServer.class);

    public void requirePubKey() {
        Single<ResponseData> single = retrofit.requirePubKey();
        single.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            //解析出公钥
                            JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                            String encode = jsonObject.getStr("pub");
                            Log.d(TAG, "onSuccess: " + encode);
                            //对客户端唯一标识进行操作
                            Secret secret = MyApplication.getInstance().getSecret();
                            //加密客户端唯一标识
                            byte[] encrypt = AsymmetryKey
                                    .encrypt(Base64.decode(secret.getClientSecret()), Base64.decode(encode));
                            String encode1 = Base64.encode(encrypt);
                            initLogin(new Secret(secret.getClientKey(), encode1));
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void initLogin(Secret secret) {
        Log.d(TAG, "initLogin: " + secret.getClientSecret());
        Single<ResponseData> single = retrofit.init(secret);

        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            ToastUtils.show(responseData.getErrMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void getNeedAudit(CallBack callBack) {
        Single<ResponseData> single = retrofit.getNeedAudit(MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY));
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                            return;
                        }
                        callBack.fail(responseData.getErrMessage());
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
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                            return;
                        }
                        callBack.fail(responseData.getErrMessage());
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
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                            return;
                        }
                        callBack.fail(responseData.getErrMessage());
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
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                            return;
                        }
                        callBack.fail(responseData.getErrMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callBack.fail(e.getMessage());
                    }
                });
    }

}
