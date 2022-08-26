package com.scut.bbs.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.scut.bbs.brief.bean.TopicRecord;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/**
 *
 * @author 徐鑫
 */
@Dao
public interface TopicRecordDAO {

    /**
     * 向数据库中插入topic record
     * @param topicRecord 操作对象
     * @return 受影响的行数
     */
    @Insert
    Completable insert(TopicRecord topicRecord);

    /**
     * 在数据库中删除记录
     * @param topicRecord 需要删除的记录
     * @return 受影响的行数
     */
    @Delete
    Single<Integer> delete(TopicRecord topicRecord);

    /**
     * 修改记录
     * @param topicRecord 需要修改的记录
     * @return 受影响的行数
     */
    @Update
    Single<Integer> update(TopicRecord topicRecord);

}
