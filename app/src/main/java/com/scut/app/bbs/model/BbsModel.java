package com.scut.app.bbs.model;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.bbs.bean.Comment;
import com.scut.app.bbs.bean.TopicPageData;
import com.scut.app.entity.ResponseData;
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
 * 用于获取后端的topics数据
 *
 * @author 徐鑫
 */
public class BbsModel {

    private static final String TAG = "BbsModel";
    ITopicServer server = NetUtils.createRetrofit(ITopicServer.class);

    public Single<TopicPageData> getTopicList(int pageNum) {
        Single<TopicPageData> topicByPage = server.getTopicByPage(pageNum, 20);
        Log.d(TAG, "getTopicList: " + pageNum);
        return topicByPage;
    }

    public void requestComment(Long topicId, CallBack callBackDeal) {
        Single<ResponseData> single = server.getCommentsByTopicId(topicId);
        single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ResponseData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull ResponseData responseData) {
                        if (responseData.getErrCode() != ResponseData.SUCCESS_CODE) {
                            callBackDeal.fail(responseData.getErrMessage());
                            return;
                        }
                        callBackDeal.success(responseData);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callBackDeal.fail(e.getMessage());
                    }
                });
    }

    public void releaseComment(Comment comment, CallBack callBack) {
        ITopicServer retrofit = NetUtils.createRetrofit(ITopicServer.class);
        String token = MyApplication.getInstance().getStr(MyApplication.TOKEN_KEY);
        Single<ResponseData> single = retrofit.releaseComment(comment, token);
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

    public void requestTopic(Long topicId, CallBack callBack) {
        ITopicServer retrofit = NetUtils.createRetrofit(ITopicServer.class);
        Single<ResponseData> single = retrofit.getTopicById(topicId);
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
