package com.scut.app.bbs.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.scut.app.MyApplication;
import com.scut.app.bbs.bean.Comment;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.bbs.model.BbsModel;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 详情页的view model
 *
 * @author 徐鑫
 */
public class TopicViewModel extends ViewModel {

    /**
     * 评论列表
     */
    private MutableLiveData<List<Comment>> commentList;
    /**
     * 详情页的帖子
     */
    private MutableLiveData<Topic> topic;

    private String comment;

    BbsModel model = new BbsModel();

    public void requestTopic(Long topicId) {
        model.requestTopic(topicId, new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                getTopic().setValue(jsonObject.getBean("topic", Topic.class));
            }

            @Override
            public void fail(String message) {

            }
        });
    }

    public void requireComment(Long topicId) {
        model.requestComment(topicId, new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                JSONObject jsonObject = JSONUtil.parseObj(responseData.getData());
                List<Comment> comments = jsonObject.getBeanList("comments", Comment.class);
                getCommentList().setValue(comments);
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        });
    }

    public void comment(CallBack callBack) {
        if (comment == null || comment.isEmpty()) {
            callBack.fail("请输入评论！");
            return;
        }
        Comment comment = new Comment();
        comment.content = this.comment;
        User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);
        comment.userId = user.id;
        Topic topic = getTopic().getValue();
        if (topic == null) {
            callBack.fail("网络繁忙，请稍后重试");
            return;
        }
        comment.topicId = topic.id;
        model.releaseComment(comment, callBack);
    }


    public MutableLiveData<List<Comment>> getCommentList() {
        if (commentList == null) {
            commentList = new MutableLiveData<>();
        }
        return commentList;
    }

    public MutableLiveData<Topic> getTopic() {
        if (topic == null) {
            topic = new MutableLiveData<>();
        }
        return topic;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
