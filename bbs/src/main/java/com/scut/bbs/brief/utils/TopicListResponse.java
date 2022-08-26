package com.scut.bbs.brief.utils;

import com.scut.bbs.entity.Topic;

import java.util.List;

/**
 * 分页topic的简单封装
 *
 * @author 徐鑫
 */
public class TopicListResponse {

    private final List<Topic> topicList;
    private final int nextPageNum;

    public TopicListResponse(List<Topic> topicList, int nextPageNum) {
        this.topicList = topicList;
        this.nextPageNum = nextPageNum;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public int getNextPageNum() {
        return nextPageNum;
    }
}
