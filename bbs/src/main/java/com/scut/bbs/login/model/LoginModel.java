package com.scut.bbs.login.model;

import android.util.Log;

import com.scut.bbs.MyApplication;
import com.scut.bbs.entity.ResponseData;
import com.scut.bbs.entity.User;
import com.scut.bbs.login.bean.LoginBean;
import com.scut.bbs.net.IUserServer;
import com.scut.bbs.room.database.UserDatabase;
import com.scut.bbs.util.NetUtils;
import com.scut.bbs.util.SharedPreferenceUtils;

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
                            return;
                        }
                        iLoginDataCallback.onLoginState(new LoginMsg(false, responseData.getMessage()));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause() + "\n" + e.getMessage());
                        iLoginDataCallback.onLoginState(new LoginMsg(false, e.getMessage()));
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
                        iLoginDataCallback.onLoginState(new LoginMsg(false, "数据库出错"));
                    }
                });
        iLoginDataCallback.onLoginState(new LoginMsg(true, "登录成功"));
    }

    /**
     * 用于回调数据
     */
    public interface ILoginDataCallback {
        /**
         * 由用户实现的数据处理
         *
         * @param msg 登录是否成功
         */
        void onLoginState(LoginMsg msg);
    }

    public static class LoginMsg {
        private final boolean success;
        private final String msg;

        public LoginMsg(boolean success, String msg) {
            this.success = success;
            this.msg = msg;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMsg() {
            return msg;
        }
    }
}
