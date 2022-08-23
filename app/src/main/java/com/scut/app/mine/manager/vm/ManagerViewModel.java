package com.scut.app.mine.manager.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.net.CallBack;
import com.scut.app.net.UserServer;
import com.scut.app.util.ToastUtils;

import java.util.Collections;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

public class ManagerViewModel extends ViewModel {

    private MutableLiveData<List<User>> needToAuditList;
    private MutableLiveData<List<User>> finishAuditList;

    private final UserServer userServer = new UserServer();

    /**
     * 请求需要审核的账号
     */
    public void requireNeedToAudit() {
        userServer.getNeedAudit(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                List<User> users = jsonObject.getBeanList("users", User.class);
                if (users == null) {
                    users = Collections.emptyList();
                }
                getNeedToAuditList().setValue(users);
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        });
    }

    public void requireFinishAudit() {
        userServer.getFinishAudit(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                List<User> users = jsonObject.getBeanList("users", User.class);
                if (users == null) {
                    users = Collections.emptyList();
                }
                getFinishAuditList().setValue(users);
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

    public void commitRollback(List<String> ids, CallBack callBack) {
        userServer.commitRollback(ids, callBack);
    }

    public MutableLiveData<List<User>> getNeedToAuditList() {
        if (needToAuditList == null) {
            needToAuditList = new MutableLiveData<>();
        }
        return needToAuditList;
    }

    public MutableLiveData<List<User>> getFinishAuditList() {
        if (finishAuditList == null) {
            finishAuditList = new MutableLiveData<>();
        }
        return finishAuditList;
    }
}
