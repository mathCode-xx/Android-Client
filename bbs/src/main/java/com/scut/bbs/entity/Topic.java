package com.scut.bbs.entity;

import androidx.lifecycle.MutableLiveData;

import com.scut.bbs.brief.bean.TopicRecord;

/**
 * 主贴实体类
 *
 * @author 徐鑫
 */
public class Topic {
    public long id;
    public String content;
    /**
     * 评论数
     */
    public int commentCount;
    /**
     * 点赞数
     */
    public int likeCount;
    /**
     * 收藏数
     */
    public int collectionCount;
    public String userId;
    public int typeId;

    private MutableLiveData<TopicRecord> topicRecord;

    public MutableLiveData<TopicRecord> getTopicRecord() {
        if (topicRecord == null) {
            TopicRecord tr = new TopicRecord();
            tr.topicId = this.id;
            tr.collected = false;
            tr.liked = false;
            this.topicRecord = new MutableLiveData<>(tr);
        }
        return topicRecord;
    }
}
