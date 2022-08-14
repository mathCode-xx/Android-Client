package com.scut.app.bbs.bean;

import androidx.lifecycle.MutableLiveData;

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
    public String time;
    public String updateTime;
}
