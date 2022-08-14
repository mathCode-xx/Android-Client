package com.scut.app.bbs.model;

import android.util.Log;

import com.scut.app.bbs.bean.TopicPageData;
import com.scut.app.net.ITopicServer;
import com.scut.app.util.NetUtils;

import io.reactivex.rxjava3.core.Single;

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
        Log.d(TAG, "getTopicList: " + topicByPage);
        return topicByPage;
    }
}
