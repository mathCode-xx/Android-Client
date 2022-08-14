package com.scut.bbs.login.vm;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.scut.bbs.brief.BriefActivity;
import com.scut.bbs.entity.User;
import com.scut.bbs.login.bean.LoginBean;
import com.scut.bbs.login.model.LoginModel;
import com.scut.bbs.room.database.UserDatabase;
import com.scut.bbs.util.SharedPreferenceUtils;
import com.scut.bbs.util.ToastUtils;

import cn.hutool.core.util.StrUtil;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 登录界面的vm
 *
 * @author 徐鑫
 */
public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<LoginBean> loginMsg;

    private MutableLiveData<Boolean> rememberPassword;
    private MutableLiveData<Boolean> success;

    public LoginModel loginModel;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.loginModel = new LoginModel();
        initData();
    }

    public void login() {
        //将是否记住密码存入配置文件
        SharedPreferenceUtils.getSharedPreference().edit()
                .putBoolean(SharedPreferenceUtils.REMEMBER_KEY
                        , rememberPassword.getValue() != null && rememberPassword.getValue())
                .apply();
        LoginBean value = loginMsg.getValue();
        if (value == null) {
            //检测loginMsg是否丢失，如果丢失，说明代码有问题，需要debug
            ToastUtils.show("系统错误，请联系系统管理员！");
            return;
        }
        if (value.getId() == null || value.getId().length() != User.ID_LENGTH) {
            //检测用户名格式是否正确，12位数字
            ToastUtils.show("用户名格式错误，需要输入12位数字的学号");
            return;
        }
        if (StrUtil.isEmpty(value.getPassword())) {
            //检测密码是否输入
            ToastUtils.show("请输入密码");
            return;
        }
        loginModel.login(loginMsg.getValue(), msg -> {
            if (msg.isSuccess()) {
                Intent intent = new Intent(getApplication(), BriefActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplication().startActivity(intent);
                getSuccess().setValue(true);
            }
            ToastUtils.show(getApplication(), msg.getMsg());
        });
    }

    /**
     * 初始化登录界面的数据
     */
    private void initData() {
        if (this.loginMsg == null) {
            this.loginMsg = new MutableLiveData<>(new LoginBean());
        }
        if (this.rememberPassword == null) {
            this.rememberPassword = new MutableLiveData<>(false);
        }
        //读取配置文件，上一次登录是否记住了密码
        rememberPassword.setValue(SharedPreferenceUtils.getSharedPreference()
                .getBoolean(SharedPreferenceUtils.REMEMBER_KEY, false));
        //读取数据库的用户信息，并显示最近登录的用户到界面上
        Single<User> userSingle = UserDatabase.getInstance().userDAO().selectOne();
        userSingle.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<User>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull User user) {
                if (loginMsg.getValue() == null) {
                    loginMsg.setValue(new LoginBean());
                }
                loginMsg.getValue().setId(user.id);
                loginMsg.getValue().setPassword(user.password);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d("LoginViewModel", "数据读取失败" + e.getCause() + e.getMessage());
            }
        });
    }

    public MutableLiveData<LoginBean> getLoginMsg() {
        if (this.loginMsg == null) {
            this.loginMsg = new MutableLiveData<>(new LoginBean());
        }
        return loginMsg;
    }

    public void setLoginMsg(MutableLiveData<LoginBean> loginMsg) {
        this.loginMsg = loginMsg;
    }

    public MutableLiveData<Boolean> getRememberPassword() {
        if (this.rememberPassword == null) {
            this.rememberPassword = new MutableLiveData<>(false);
        }
        return rememberPassword;
    }

    public void setRememberPassword(MutableLiveData<Boolean> rememberPassword) {
        this.rememberPassword = rememberPassword;
    }

    public MutableLiveData<Boolean> getSuccess() {
        if (this.success == null) {
            this.success = new MutableLiveData<>(false);
        }
        return success;
    }

    public void setSuccess(MutableLiveData<Boolean> success) {
        this.success = success;
    }
}
