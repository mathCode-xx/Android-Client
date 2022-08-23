package com.scut.app.net;

import com.scut.app.bbs.bean.Comment;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.bbs.bean.TopicPageData;
import com.scut.app.entity.ResponseData;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    @GET("topic/user_id/{id}")
    Single<ResponseData> getTopicByUserId(@Path("id") String id);

    @GET("topic/id/{id}")
    Single<ResponseData> getTopicById(@Path("id") Long id);

    @GET("/comment/topic/{id}")
    Single<ResponseData> getCommentsByTopicId(@Path("id") Long id);

    @POST("topic")
    Single<ResponseData> release(@Body Topic topic, @Header("token") String token);

    @POST("comment")
    Single<ResponseData> releaseComment(@Body Comment comment, @Header("token") String token);

    @HTTP(method = "DELETE", path = "topic", hasBody = true)
    Single<ResponseData> delete(@Body Topic topic, @Header("token") String token);
}
