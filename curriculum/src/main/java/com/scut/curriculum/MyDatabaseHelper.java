package com.scut.curriculum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    //只需要一张表即可
    public static final String CREATE_SCHEDULE = "create table Schedule (" +
            "id integer primary key autoincrement, " +
            "class_name text, " +
            "credit float, " +
            "classroom text, " +
            "teacher text, " +
            "Week_of_class_time integer, " +
            "class_day integer, " +
            "lecture_time integer)";
    private Context mContext;

    public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SCHEDULE);
        Toast.makeText(mContext, "Created succeeded", Toast.LENGTH_SHORT).show();

    }

    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Schedule");
        onCreate(db);
    }
}
