package com.scut.app.entity;

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
    public Long topicId = 0L;

    public Boolean liked;

    public Boolean collected;
}
