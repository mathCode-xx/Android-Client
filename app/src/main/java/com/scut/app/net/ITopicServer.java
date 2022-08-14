package com.scut.app.net;

import com.scut.app.bbs.bean.TopicPageData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 与后端topic表交互的接口
 *
 * @author 徐鑫
 */
public interface ITopicServer {

    /**
     * 分页查询topic
     *
     * @param pageNum  页码
     * @param pageSize 页的大小
     * @return 响应数据
     */
    @GET("topic/page")
    Single<TopicPageData> getTopicByPage(@Query("pageNum") Integer pageNum, @Query("pageSize") Integer pageSize);
}
