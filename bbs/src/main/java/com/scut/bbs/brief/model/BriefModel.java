package com.scut.bbs.brief.model;

import android.util.Log;

import com.scut.bbs.brief.utils.TopicListResponse;
import com.scut.bbs.entity.ResponseData;
import com.scut.bbs.entity.Topic;
import com.scut.bbs.net.ITopicServer;
import com.scut.bbs.util.NetUtils;

import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.reactivex.rxjava3.core.Single;

/**
 * 用于获取后端的topics数据
 *
 * @author 徐鑫
 */
public class BriefModel {

    private static final String TAG = "BriefModel";
    ITopicServer server = NetUtils.createRetrofit(ITopicServer.class);

    public Single<TopicListResponse> getTopicList(int pageNum) {
        Single<ResponseData> topicByPage = server.getTopicByPage(pageNum, 20);
        Log.d(TAG, "getTopicList: ");

        return topicByPage.map(responseData -> {
            JSONObject jsonObject = JSONUtil.parseObj(responseData.getData().get("info"));
            if (jsonObject.getBool("isLastPage")) {
                return null;
            }
            List<Topic> topics = jsonObject.getBeanList("list", Topic.class);
            Log.d(TAG, topics.toString());
            return new TopicListResponse(topics, pageNum + 1);
        });
    }

    public Single<Boolean> like(Long topicId) {
        return null;
    }
}
