package com.scut.app.room.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.scut.app.MyApplication;
import com.scut.app.entity.User;
import com.scut.app.room.dao.UserDAO;

/**
 * user数据库
 * @author 徐鑫
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static class Instance {
        private static final UserDatabase INSTANCE
                = Room.databaseBuilder(MyApplication.getInstance(), UserDatabase.class, "user_database")
                .build();
    }

    public static UserDatabase getInstance() {
        return Instance.INSTANCE;
    }

    /**
     * 返回dao对象
     * @return 返回dao对象
     */
    public abstract UserDAO userDAO();
}
