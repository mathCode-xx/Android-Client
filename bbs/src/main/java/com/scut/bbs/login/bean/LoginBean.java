package com.scut.bbs.login.bean;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.scut.bbs.BR;


/**
 * 登录时使用的java类
 *
 * @author 徐鑫
 */
public class LoginBean extends BaseObservable {
    private String id;
    private String password;

    public LoginBean(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public LoginBean() {
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
