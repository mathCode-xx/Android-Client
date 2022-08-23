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

public class change_class_information_Activity extends AppCompatActivity {
    private ImageButton findDevices;
    private SQLiteDatabase dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_class_information);

        String databases_path = getDatabasePath("Schedule.db").toString();
        dbHelper = SQLiteDatabase.openDatabase(databases_path, null, SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING);
        EditText class_name = (EditText) findViewById(R.id.class_name);
        EditText credit = (EditText) findViewById(R.id.credit);
        EditText classroom = (EditText) findViewById(R.id.classroom);
        EditText teacher = (EditText) findViewById(R.id.teacher);
        EditText Week_of_class_time = (EditText) findViewById(R.id.Week_of_class_time);
        EditText class_day = (EditText) findViewById(R.id.class_day);
        EditText lecture_time = (EditText) findViewById(R.id.lecture_time);



        Button search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String class_name_1 = class_name.getText().toString();
                String credit_1 = credit.getText().toString();
                String classroom_1 = classroom.getText().toString();
                String teacher_1 = teacher.getText().toString();
                String Week_of_class_time_1 = Week_of_class_time.getText().toString();
                String class_day_1 = class_day.getText().toString();
                String lecture_time_1 = lecture_time.getText().toString();

                Float credit_2 = Float.parseFloat(credit_1);//数据类型转换
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
                            Toast.makeText(change_class_information_Activity.this, "该时段已有课程，可进行课程信息的更改", Toast.LENGTH_SHORT).show();
                        }

                    } while (cursor.moveToNext());


                }
                Toast.makeText(change_class_information_Activity.this, "若仅出现此弹窗说明该时段无课程，不可更改课程信息，若有两次弹窗出现则可更改该时段课程信息", Toast.LENGTH_SHORT).show();

            }
        });

        Button change_information = findViewById(R.id.change_information);
        change_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String class_name_1 = class_name.getText().toString();
                String credit_1 = credit.getText().toString();
                String classroom_1 = classroom.getText().toString();
                String teacher_1 = teacher.getText().toString();
                String Week_of_class_time_1 = Week_of_class_time.getText().toString();
                String class_day_1 = class_day.getText().toString();
                String lecture_time_1 = lecture_time.getText().toString();

                SQLiteDatabase db = dbHelper;
                ContentValues values = new ContentValues();
                values.put("class_name", class_name_1);
                values.put("credit", credit_1);
                values.put("classroom", classroom_1);
                values.put("teacher", teacher_1);
                values.put("Week_of_class_time", Week_of_class_time_1);
                values.put("class_day", class_day_1);
                values.put("lecture_time", lecture_time_1);
                db.update("Schedule", values, "Week_of_class_time = ? and class_day = ? and lecture_time = ?",new String[] {Week_of_class_time_1,class_day_1,lecture_time_1});
                values.clear();
                Toast.makeText(change_class_information_Activity.this, "成功更改课程信息!", Toast.LENGTH_SHORT).show();
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