package com.scut.app.room.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.scut.app.MyApplication;
import com.scut.app.entity.TopicRecord;
import com.scut.app.room.dao.TopicRecordDAO;

/**
 * topic record 数据库管理
 *
 * @author 徐鑫
 */
@Database(entities = {TopicRecord.class}, version = 1, exportSchema = false)
public abstract class TopicRecordDatabase extends RoomDatabase {
    private static class Instance {
        private static final TopicRecordDatabase INSTANCE
                = Room.databaseBuilder(MyApplication.getInstance(), TopicRecordDatabase.class, "topic_record_database")
                .build();
    }

    public static TopicRecordDatabase getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 返回dao对象
     *
     * @return 返回dao对象
     */
    public abstract TopicRecordDAO topicRecordDAO();
}
