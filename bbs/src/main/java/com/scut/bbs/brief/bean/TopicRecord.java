package com.scut.bbs.brief.bean;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 记录该用户对topic的操作，包括点赞、收藏
 * @author 徐鑫
 */
@Entity(indices = {@Index("topicId")})
public class TopicRecord {
    @PrimaryKey
    @NonNull
    public Long topicId;

    public Boolean liked;

    public Boolean collected;

    public TopicRecord() {
        this.topicId = 0L;
    }

    public TopicRecord(@NonNull Long topicId, Boolean liked, Boolean collected) {
        this.topicId = topicId;
        this.liked = liked;
        this.collected = collected;
    }
}
