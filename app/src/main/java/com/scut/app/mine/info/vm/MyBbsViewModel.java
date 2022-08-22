package com.scut.app.mine.info.vm;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scut.app.bbs.bean.Topic;
import com.scut.app.entity.ResponseData;
import com.scut.app.mine.info.model.InfoModel;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 管理我的bbs
 * @author 徐鑫
 */
public class MyBbsViewModel extends ViewModel {
    private static final String TAG = "MyBbsViewModel";

    private MutableLiveData<List<Topic>> myTopics;
    private final InfoModel model = new InfoModel();

    public void requireMyTopic() {
        model.requireMyTopic(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject data = JSONUtil.parseObj(responseData.getData());
                List<Topic> topics = data.getBeanList("topics", Topic.class);
                myTopics.setValue(topics);
            }

            @Override
            public void fail(String message) {
                Log.d(TAG, "请求我的帖子失败");
            }
        });
    }

    public void deleteTopic(Topic topic, CallBack callBack) {
        model.deleteTopic(topic, callBack);
    }

    public MutableLiveData<List<Topic>> getMyTopics() {
        if (myTopics == null) {
            myTopics = new MutableLiveData<>();
        }
        return myTopics;
    }
}
