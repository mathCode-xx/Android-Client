package com.scut.app.bbs.vm;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.scut.app.MyApplication;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.net.CallBack;
import com.scut.app.net.ITopicServer;
import com.scut.app.util.NetUtils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * 发布帖子
 * @author 徐鑫
 */
public class ReleaseViewModel extends ViewModel {
    private static final String TAG = "ReleaseViewModel";

    private String topicContent;

    public void release(CallBack callBack) {
        ITopicServer retrofit = NetUtils.createRetrofit(ITopicServer.class);
        User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);
        //封装数据
        Topic topic = new Topic();
        topic.content = getTopicContent();
        topic.userId = user.id;
        topic.typeId = 1;
        //获取token
        String token = MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY);
        Single<ResponseData> single = retrofit.release(topic, token);
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getErrCode() == ResponseData.SUCCESS_CODE) {
                            callBack.success(responseData);
                        } else {
                            callBack.fail(responseData.getErrMessage());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getCause());
                        callBack.fail(e.getMessage());
                    }
                });
    }


    public void setTopicContent(String topicContent) {
        this.topicContent = topicContent;
    }

    public String getTopicContent() {
        return topicContent;
    }
}
