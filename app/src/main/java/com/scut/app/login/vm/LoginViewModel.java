package com.scut.app.login.vm;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.scut.app.entity.LoginBean;
import com.scut.app.entity.User;
import com.scut.app.login.model.LoginModel;
import com.scut.app.room.database.UserDatabase;
import com.scut.app.util.SharedPreferenceUtils;
import com.scut.app.util.ToastUtils;

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

    public LoginModel loginModel;

    private User user = new User();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.loginModel = new LoginModel();
        initData();
    }

    public void login(LoginModel.ILoginDataCallback callback) {
        //将是否记住密码存入配置文件
        SharedPreferenceUtils.putBoolean(SharedPreferenceUtils.REMEMBER_KEY
                , rememberPassword.getValue() != null && rememberPassword.getValue());

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
        loginModel.login(loginMsg.getValue(), callback);
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
        rememberPassword.setValue(SharedPreferenceUtils
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

    public void register(LoginModel.CallBackRegister callBackRegister) {
        if (StrUtil.isEmpty(user.id) || StrUtil.isEmpty(user.college)
                || StrUtil.isEmpty(user.password) || StrUtil.isEmpty(user.name)
                || StrUtil.isEmpty(user.major)) {
            ToastUtils.show("请完善输入");
            return;
        }
        loginModel.register(user, callBackRegister);
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

    public User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
