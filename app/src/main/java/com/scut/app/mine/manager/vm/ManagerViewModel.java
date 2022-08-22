package com.scut.app.mine.manager.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.net.CallBack;
import com.scut.app.net.UserServer;
import com.scut.app.util.ToastUtils;

import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class ManagerViewModel extends ViewModel {

    private MutableLiveData<List<User>> userList;

    private final UserServer userServer = new UserServer();

    /**
     * 请求需要审核的账号
     */
    public void requireUser() {
        userServer.getNeedAudit(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                List<User> users = jsonObject.getBeanList("users", User.class);
                getUserList().setValue(users);
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        });
    }

    public void commitAudit(List<String> ids, CallBack callBack) {
        userServer.commitAudit(ids, callBack);
    }

    public MutableLiveData<List<User>> getUserList() {
        if (userList == null) {
            userList = new MutableLiveData<>();
        }
        return userList;
    }
}
