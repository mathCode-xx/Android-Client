package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Objects;

public class delete_class_information_Activity extends AppCompatActivity {
    private ImageButton findDevices;
    private SQLiteDatabase dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class_information);

        String databases_path = getDatabasePath("Schedule.db").toString();
        dbHelper = SQLiteDatabase.openDatabase(databases_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        EditText Week_of_class_time = (EditText) findViewById(R.id.Week_of_class_time);
        EditText class_day = (EditText) findViewById(R.id.class_day);
        EditText lecture_time = (EditText) findViewById(R.id.lecture_time);
        EditText class_name = (EditText) findViewById(R.id.class_name);


        Button search_1 = findViewById(R.id.search_1);
        search_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String class_name_1 = class_name.getText().toString();


                SQLiteDatabase db = dbHelper;
                //查询所有的数据
                Cursor cursor = db.query("Schedule", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {

                        @SuppressLint("Range") String classname = cursor.getString(cursor.getColumnIndex("class_name"));


                        if (Objects.equals(classname, class_name_1)) {
                            Toast.makeText(delete_class_information_Activity.this, "有该课程", Toast.LENGTH_SHORT).show();
                        }
                    } while (cursor.moveToNext());

                }
            }
        });


        Button search_2 = findViewById(R.id.search_2);
        search_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Week_of_class_time_1 = Week_of_class_time.getText().toString();
                String class_day_1 = class_day.getText().toString();
                String lecture_time_1 = lecture_time.getText().toString();
                String class_name_1 = class_name.getText().toString();

                int Week_of_class_time_2 = Integer.parseInt(Week_of_class_time_1);
                int class_day_2 = Integer.parseInt(class_day_1);
                int lecture_time_2 = Integer.parseInt(lecture_time_1);

                SQLiteDatabase db = dbHelper;
                //查询所有的数据
                Cursor cursor = db.query("Schedule", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String week_time = cursor.getString(cursor.getColumnIndex("Week_of_class_time"));
                        @SuppressLint("Range") String classday = cursor.getString(cursor.getColumnIndex("class_day"));
                        @SuppressLint("Range") String lecturetime = cursor.getString(cursor.getColumnIndex("lecture_time"));

                        int Week_of_class_time_3 = Integer.parseInt(week_time);
                        int class_day_3 = Integer.parseInt(classday);
                        int lecture_time_3 = Integer.parseInt(lecturetime);

                        if (Week_of_class_time_2 == Week_of_class_time_3 && class_day_3 == class_day_2 && lecture_time_3 == lecture_time_2) {
                            Toast.makeText(delete_class_information_Activity.this, "该时段有课程", Toast.LENGTH_SHORT).show();
                        }
                    } while (cursor.moveToNext());

                }
            }
        });

        Button delete_information_2 = findViewById(R.id.delete_information_2);
        delete_information_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Week_of_class_time_1 = Week_of_class_time.getText().toString();
                String class_day_1 = class_day.getText().toString();
                String lecture_time_1 = lecture_time.getText().toString();
                SQLiteDatabase db = dbHelper;
                ContentValues values = new ContentValues();


                db.delete("Schedule", "Week_of_class_time = ? and class_day = ? and lecture_time = ?",new String[] {Week_of_class_time_1,class_day_1,lecture_time_1});
                values.clear();
                Toast.makeText(delete_class_information_Activity.this, "成功删除课程信息!", Toast.LENGTH_SHORT).show();
            }
        });


        Button delete_information_1 = findViewById(R.id.delete_information_1);
        delete_information_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String class_name_1 = class_name.getText().toString();

                SQLiteDatabase db = dbHelper;
                ContentValues values = new ContentValues();


                db.delete("Schedule", "class_name = ?",new String[] {class_name_1});
                values.clear();
                Toast.makeText(delete_class_information_Activity.this, "成功删除课程信息!", Toast.LENGTH_SHORT).show();
            }
        });

        //下面是返回按钮
        findDevices = findViewById(R.id.ic_back);
        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //直接关闭当前页面
            }
        });
    }
}