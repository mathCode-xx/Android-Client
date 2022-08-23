package com.scut.app.login.model;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.entity.LoginBean;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.net.IUserServer;
import com.scut.app.room.database.UserDatabase;
import com.scut.app.util.NetUtils;
import com.scut.app.util.SharedPreferenceUtils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 登录业务
 *
 * @author 徐鑫
 */
public class LoginModel {

    private static final String TAG = "LoginModel";

    public void login(LoginBean loginBean, ILoginDataCallback iLoginDataCallback) {
        request(loginBean, iLoginDataCallback);
    }

    private void request(LoginBean loginBean, ILoginDataCallback iLoginDataCallback) {
        IUserServer iUserServer = NetUtils.createRetrofit(IUserServer.class);

        Observable<ResponseData> login = iUserServer.login(loginBean);
        login.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseData responseData) {
                        Log.d(TAG, "onNext: " + responseData.getMessage());
                        if (responseData.getStatusCode() == ResponseData.SUCCESS_CODE) {
                            saveData(loginBean, responseData, iLoginDataCallback);
                            iLoginDataCallback.success();
                            return;
                        }
                        iLoginDataCallback.fail(responseData.getMessage());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause() + "\n" + e.getMessage());
                        iLoginDataCallback.fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void saveData(LoginBean loginBean, ResponseData data, ILoginDataCallback iLoginDataCallback) {
        //借助hutools进行数据格式化，并依次获取数据
        JSONObject jsonObject = JSONUtil.parseObj(data.getData());

        String token = jsonObject.getStr("token");
        Log.d(TAG, "saveData: " + token);
        MyApplication.getInstance().put(MyApplication.TOKEN_KEY, token);

        Log.d(TAG, "saveData: " + jsonObject.getStr("user"));

        User user = jsonObject.get("user", User.class);
        MyApplication.getInstance().putObj(MyApplication.USER_KEY, user);

        //根据配置文件中的是否记住密码来决定是否将密码保存到数据库
        if (SharedPreferenceUtils.getSharedPreference()
                .getBoolean(SharedPreferenceUtils.REMEMBER_KEY, false)) {
            user.password = loginBean.getPassword();
        }
        Single<Integer> update = UserDatabase.getInstance().userDAO().update(user);
        update.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        if (integer == 0) {
                            //数据库没有数据需要插入
                            Log.d(TAG, "数据库没有数据需要插入");
                            UserDatabase.getInstance().userDAO().insert(user)
                                    .subscribeOn(Schedulers.io()).subscribe();
                        }
                        Log.d(TAG, "数据库中存在数据");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause() + "\n" + e.getMessage());
                        iLoginDataCallback.fail("数据库出错");
                    }
                });
    }

    /**
     * 用于回调数据
     */
    public interface ILoginDataCallback {
        void success();

        void fail(String message);
    }


    public void register(User user, CallBackRegister callBackRegister) {
        IUserServer retrofit = NetUtils.createRetrofit(IUserServer.class);
        Observable<ResponseData> observable = retrofit.register(user);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseData responseData) {
                        Log.d(TAG, "onNext: " + responseData.getStatusCode());
                        if (responseData.getStatusCode() != ResponseData.SUCCESS_CODE) {
                            callBackRegister.fail(responseData.getMessage());
                            return;
                        }
                        callBackRegister.success(responseData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callBackRegister.fail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public interface CallBackRegister {
        void success(ResponseData responseData);

        void fail(String message);
    }
}
