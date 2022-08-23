package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CurriculumMainActivity extends AppCompatActivity {
    private ImageButton findDevices;
    private MyDatabaseHelper dbHelper;
    public static List<Activity> activityList = new LinkedList();

    //内容数组
    String[] Array = {"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周", "第九周", "第十周", "第十一周",
            "第十二周", "第十三周", "第十四周", "第十五周", "第十六周", "第十七周", "第十八周", "第十九周", "第二十周"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_main);

        // 下面是数据库的操作，但是一旦建立好数据库之后就不用建立了，只需要插入数据就行
        dbHelper = new MyDatabaseHelper(this, "Schedule.db", null, 7);
        ImageButton createDatabase = (ImageButton) findViewById(R.id.load);
        //通过点击事件得到一个类
        createDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 必须执行操作之后才能被创建
                dbHelper.getWritableDatabase();
            }
        });

        //下面是插入数据的方式
        TextView addData = (TextView) findViewById(R.id.insert);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                //开始组装数据，我做一个示范
                values.put("class_name", "高等代数习题课");
                values.put("credit", 1);
                values.put("classroom", "博学楼101");
                values.put("teacher", "陈六");
                values.put("Week_of_class_time", 20);
                values.put("class_day", 7);
                values.put("lecture_time", 12);
                db.insert("Schedule", null, values);
                values.clear();

                for (int i = 1; i <= 16; i++) {
                    for (int j = 1; j <= 2; j++) {
                        values.put("class_name", "复变函数");
                        values.put("credit", 4);
                        values.put("classroom", "博学楼412");
                        values.put("teacher", "武文");
                        values.put("Week_of_class_time", i);
                        values.put("class_day", 1);
                        values.put("lecture_time", j);
                        db.insert("Schedule", null, values);
                        values.clear();
                    }
                    for (int j = 3; j <= 4; j++) {
                        values.put("class_name", "复变函数");
                        values.put("credit", 4);
                        values.put("classroom", "博学楼412");
                        values.put("teacher", "武文");
                        values.put("Week_of_class_time", i);
                        values.put("class_day", 4);
                        values.put("lecture_time", j);
                        db.insert("Schedule", null, values);
                        values.clear();
                    }
                }


                for (int i = 1; i <= 16; i++) {
                    values.put("class_name", "数学分析选讲");
                    values.put("credit", 3);
                    values.put("classroom", "博学楼103");
                    values.put("teacher", "张三");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 1);
                    values.put("lecture_time", 7);
                    db.insert("Schedule", null, values);
                    values.clear();

                    values.put("class_name", "数学分析选讲");
                    values.put("credit", 3);
                    values.put("classroom", "博学楼103");
                    values.put("teacher", "张三");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 1);
                    values.put("lecture_time", 8);
                    db.insert("Schedule", null, values);
                    values.clear();

                    values.put("class_name", "计算机原理");
                    values.put("credit", 4);
                    values.put("classroom", "博学楼203");
                    values.put("teacher", "李四");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 4);
                    values.put("lecture_time", 5);
                    db.insert("Schedule", null, values);
                    values.clear();

                    values.put("class_name", "计算机原理");
                    values.put("credit", 4);
                    values.put("classroom", "博学楼203");
                    values.put("teacher", "李四");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 4);
                    values.put("lecture_time", 6);
                    db.insert("Schedule", null, values);
                    values.clear();

                    values.put("class_name", "操作系统");
                    values.put("credit", 4);
                    values.put("classroom", "博学楼306");
                    values.put("teacher", "王五");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 3);
                    values.put("lecture_time", 3);
                    db.insert("Schedule", null, values);
                    values.clear();

                    values.put("class_name", "操作系统");
                    values.put("credit", 4);
                    values.put("classroom", "博学楼306");
                    values.put("teacher", "王五");
                    values.put("Week_of_class_time", i);
                    values.put("class_day", 3);
                    values.put("lecture_time", 4);
                    db.insert("Schedule", null, values);
                    values.clear();
                }


            }
        });

        //下面写增删改页面的转移按钮
        ImageButton imageButton = (ImageButton) findViewById(R.id.setting);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurriculumMainActivity.this, ChangeActivity.class);
                startActivity(intent);
            }
        });

// 删除所有数据的写法
        TextView deleteData = (TextView) findViewById(R.id.delete);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Schedule", "class_day = ?", new String[]{"1"});
                db.delete("Schedule", "class_day = ?", new String[]{"2"});
                db.delete("Schedule", "class_day = ?", new String[]{"3"});
                db.delete("Schedule", "class_day = ?", new String[]{"4"});
                db.delete("Schedule", "class_day = ?", new String[]{"5"});
                db.delete("Schedule", "class_day = ?", new String[]{"6"});
                db.delete("Schedule", "class_day = ?", new String[]{"7"});
            }
        });


        Button button2 = findViewById(R.id.second);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurriculumMainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.third);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurriculumMainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        Button button4 = findViewById(R.id.fourth);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CurriculumMainActivity.this, Setting.class);
                startActivity(intent);
            }
        });
        findDevices = findViewById(R.id.ic_back);
        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //直接关闭当前页面
            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        //数组适配器
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Array);
        spinner.setAdapter(arrayAdapter);
        //设置默认选中项
        spinner.setSelection(0);
        Button buttonok = findViewById(R.id.accept);
        //设置按钮监听器
        buttonok.setOnClickListener(new MyonClickListener());
        // 定义按钮点击监听器


        CurriculumMainActivity.activityList.add(this);
    }


    private class MyonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.accept) {
                //获取选中项
                Spinner spinner = findViewById(R.id.spinner);
                //这里要留着修改课表的项目
                Toast.makeText(CurriculumMainActivity.this, Array[spinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();
                //先刷新，使得课程表上显示为空
                TextView view11 = findViewById(R.id.class_1_1);
                view11.setText(" ");
                TextView view12 = findViewById(R.id.class_1_2);
                view12.setText(" ");
                TextView view13 = findViewById(R.id.class_1_3);
                view13.setText(" ");
                TextView view14 = findViewById(R.id.class_1_4);
                view14.setText(" ");
                TextView view15 = findViewById(R.id.class_1_5);
                view15.setText(" ");
                TextView view16 = findViewById(R.id.class_1_6);
                view16.setText(" ");
                TextView view17 = findViewById(R.id.class_1_7);
                view17.setText(" ");
                TextView view18 = findViewById(R.id.class_1_8);
                view18.setText(" ");
                TextView view19 = findViewById(R.id.class_1_9);
                view19.setText(" ");
                TextView view110 = findViewById(R.id.class_1_10);
                view110.setText(" ");
                TextView view111 = findViewById(R.id.class_1_11);
                view111.setText(" ");
                TextView view112 = findViewById(R.id.class_1_12);
                view112.setText(" ");
                TextView view21 = findViewById(R.id.class_2_1);
                view21.setText(" ");
                TextView view22 = findViewById(R.id.class_2_2);
                view22.setText(" ");
                TextView view23 = findViewById(R.id.class_2_3);
                view23.setText(" ");
                TextView view24 = findViewById(R.id.class_2_4);
                view24.setText(" ");
                TextView view25 = findViewById(R.id.class_2_5);
                view25.setText(" ");
                TextView view26 = findViewById(R.id.class_2_6);
                view26.setText(" ");
                TextView view27 = findViewById(R.id.class_2_7);
                view27.setText(" ");
                TextView view28 = findViewById(R.id.class_2_8);
                view28.setText(" ");
                TextView view29 = findViewById(R.id.class_2_9);
                view29.setText(" ");
                TextView view210 = findViewById(R.id.class_2_10);
                view210.setText(" ");
                TextView view211 = findViewById(R.id.class_2_11);
                view211.setText(" ");
                TextView view212 = findViewById(R.id.class_2_12);
                view212.setText(" ");
                TextView view31 = findViewById(R.id.class_3_1);
                view31.setText(" ");
                TextView view32 = findViewById(R.id.class_3_2);
                view32.setText(" ");
                TextView view33 = findViewById(R.id.class_3_3);
                view33.setText(" ");
                TextView view34 = findViewById(R.id.class_3_4);
                view34.setText(" ");
                TextView view35 = findViewById(R.id.class_3_5);
                view35.setText(" ");
                TextView view36 = findViewById(R.id.class_3_6);
                view36.setText(" ");
                TextView view37 = findViewById(R.id.class_3_7);
                view37.setText(" ");
                TextView view38 = findViewById(R.id.class_3_8);
                view38.setText(" ");
                TextView view39 = findViewById(R.id.class_3_9);
                view39.setText(" ");
                TextView view310 = findViewById(R.id.class_3_10);
                view310.setText(" ");
                TextView view311 = findViewById(R.id.class_3_11);
                view311.setText(" ");
                TextView view312 = findViewById(R.id.class_3_12);
                view312.setText(" ");
                TextView view41 = findViewById(R.id.class_4_1);
                view41.setText(" ");
                TextView view42 = findViewById(R.id.class_4_2);
                view42.setText(" ");
                TextView view43 = findViewById(R.id.class_4_3);
                view43.setText(" ");
                TextView view44 = findViewById(R.id.class_4_4);
                view44.setText(" ");
                TextView view45 = findViewById(R.id.class_4_5);
                view45.setText(" ");
                TextView view46 = findViewById(R.id.class_4_6);
                view46.setText(" ");
                TextView view47 = findViewById(R.id.class_4_7);
                view47.setText(" ");
                TextView view48 = findViewById(R.id.class_4_8);
                view48.setText(" ");
                TextView view49 = findViewById(R.id.class_4_9);
                view49.setText(" ");
                TextView view410 = findViewById(R.id.class_4_10);
                view410.setText(" ");
                TextView view411 = findViewById(R.id.class_4_11);
                view411.setText(" ");
                TextView view412 = findViewById(R.id.class_4_12);
                view412.setText(" ");
                TextView view51 = findViewById(R.id.class_5_1);
                view51.setText(" ");
                TextView view52 = findViewById(R.id.class_5_2);
                view52.setText(" ");
                TextView view53 = findViewById(R.id.class_5_3);
                view53.setText(" ");
                TextView view54 = findViewById(R.id.class_5_4);
                view54.setText(" ");
                TextView view55 = findViewById(R.id.class_5_5);
                view55.setText(" ");
                TextView view56 = findViewById(R.id.class_5_6);
                view56.setText(" ");
                TextView view57 = findViewById(R.id.class_5_7);
                view57.setText(" ");
                TextView view58 = findViewById(R.id.class_5_8);
                view58.setText(" ");
                TextView view59 = findViewById(R.id.class_5_9);
                view59.setText(" ");
                TextView view510 = findViewById(R.id.class_5_10);
                view510.setText(" ");
                TextView view511 = findViewById(R.id.class_5_11);
                view511.setText(" ");
                TextView view512 = findViewById(R.id.class_5_12);
                view512.setText(" ");
                TextView view61 = findViewById(R.id.class_6_1);
                view61.setText(" ");
                TextView view62 = findViewById(R.id.class_6_2);
                view62.setText(" ");
                TextView view63 = findViewById(R.id.class_6_3);
                view63.setText(" ");
                TextView view64 = findViewById(R.id.class_6_4);
                view64.setText(" ");
                TextView view65 = findViewById(R.id.class_6_5);
                view65.setText(" ");
                TextView view66 = findViewById(R.id.class_6_6);
                view66.setText(" ");
                TextView view67 = findViewById(R.id.class_6_7);
                view67.setText(" ");
                TextView view68 = findViewById(R.id.class_6_8);
                view68.setText(" ");
                TextView view69 = findViewById(R.id.class_6_9);
                view69.setText(" ");
                TextView view610 = findViewById(R.id.class_6_10);
                view610.setText(" ");
                TextView view611 = findViewById(R.id.class_6_11);
                view611.setText(" ");
                TextView view612 = findViewById(R.id.class_6_12);
                view612.setText(" ");
                TextView view71 = findViewById(R.id.class_7_1);
                view71.setText(" ");
                TextView view72 = findViewById(R.id.class_7_2);
                view72.setText(" ");
                TextView view73 = findViewById(R.id.class_7_3);
                view73.setText(" ");
                TextView view74 = findViewById(R.id.class_7_4);
                view74.setText(" ");
                TextView view75 = findViewById(R.id.class_7_5);
                view75.setText(" ");
                TextView view76 = findViewById(R.id.class_7_6);
                view76.setText(" ");
                TextView view77 = findViewById(R.id.class_7_7);
                view77.setText(" ");
                TextView view78 = findViewById(R.id.class_7_8);
                view78.setText(" ");
                TextView view79 = findViewById(R.id.class_7_9);
                view79.setText(" ");
                TextView view710 = findViewById(R.id.class_7_10);
                view710.setText(" ");
                TextView view711 = findViewById(R.id.class_7_11);
                view711.setText(" ");
                TextView view712 = findViewById(R.id.class_7_12);
                view712.setText(" ");


                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Schedule", null, null, null, null, null, null);


                if (cursor.moveToFirst()) {
                    do {
                        @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));
                        @SuppressLint("Range") String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
                        @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
                        @SuppressLint("Range") int Week_of_class_time = cursor.getInt(cursor.getColumnIndex("Week_of_class_time"));
                        @SuppressLint("Range") int class_day = cursor.getInt(cursor.getColumnIndex("class_day"));
                        @SuppressLint("Range") int lecture_time = cursor.getInt(cursor.getColumnIndex("lecture_time"));

                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 1) {
                            view11.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 2) {
                            view12.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 3) {
                            view13.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 4) {
                            view14.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 5) {
                            view15.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 6) {
                            view16.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 7) {
                            view17.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 8) {
                            view18.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 9) {
                            view19.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 10) {
                            view110.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 11) {
                            view111.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 1 && lecture_time == 12) {
                            view112.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 1) {
                            view21.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 2) {
                            view22.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 3) {
                            view23.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 4) {
                            view24.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 5) {
                            view25.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 6) {
                            view26.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 7) {
                            view27.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 8) {
                            view28.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 9) {
                            view29.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 10) {
                            view210.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 11) {
                            view211.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 2 && lecture_time == 12) {
                            view212.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 1) {
                            view31.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 2) {
                            view32.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 3) {
                            view33.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 4) {
                            view34.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 5) {
                            view35.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 6) {
                            view36.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 7) {
                            view37.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 8) {
                            view38.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 9) {
                            view39.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 10) {
                            view310.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 11) {
                            view311.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 3 && lecture_time == 12) {
                            view312.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 1) {
                            view41.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 2) {
                            view42.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 3) {
                            view43.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 4) {
                            view44.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 5) {
                            view45.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 6) {
                            view46.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 7) {
                            view47.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 8) {
                            view48.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 9) {
                            view49.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 10) {
                            view410.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 11) {
                            view411.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 4 && lecture_time == 12) {
                            view412.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 1) {
                            view51.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 2) {
                            view52.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 3) {
                            view53.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 4) {
                            view54.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 5) {
                            view55.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 6) {
                            view56.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 7) {
                            view57.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 8) {
                            view58.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 9) {
                            view59.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 10) {
                            view510.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 11) {
                            view511.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 5 && lecture_time == 12) {
                            view512.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 1) {
                            view61.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 2) {
                            view62.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 3) {
                            view63.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 4) {
                            view64.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 5) {
                            view65.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 6) {
                            view66.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 7) {
                            view67.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 8) {
                            view68.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 9) {
                            view69.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 10) {
                            view610.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 11) {
                            view611.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 6 && lecture_time == 12) {
                            view612.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }


                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 1) {
                            view71.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 2) {
                            view72.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 3) {
                            view73.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 4) {
                            view74.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 5) {
                            view75.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 6) {
                            view76.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 7) {
                            view77.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 8) {
                            view78.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 9) {
                            view79.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 10) {
                            view710.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 11) {
                            view711.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }
                        if ((Week_of_class_time == (spinner.getSelectedItemPosition() + 1)) && class_day == 7 && lecture_time == 12) {
                            view712.setText(class_name + '\n' + classroom + '\n' + teacher);
                        }

                    } while (cursor.moveToNext());

                }

                view11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view11.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view12.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view13.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view14.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view14.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view15.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view15.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view16.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view16.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view17.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view17.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view18.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view18.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view19.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view19.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view110.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view110.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view111.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view111.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view112.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view112.getText(), Toast.LENGTH_SHORT).show();
                    }
                });


                view21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view21.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view22.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view22.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view23.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view23.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view24.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view24.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view25.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view25.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view26.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view26.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view27.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view27.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view28.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view28.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view29.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view29.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view210.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view210.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view211.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view211.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view212.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view212.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                view31.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view31.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view32.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view32.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view33.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view33.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view34.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view34.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view35.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view35.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view36.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view36.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view37.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view37.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view38.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view38.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view39.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view39.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view310.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view310.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view311.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view311.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view312.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view312.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                view41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view41.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view42.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view42.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view43.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view43.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view44.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view44.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view45.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view45.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view46.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view46.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view47.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view47.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view48.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view48.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view49.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view49.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view410.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view410.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view411.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view411.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view412.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view412.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                view51.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view51.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view52.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view52.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view53.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view53.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view54.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view54.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view55.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view55.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view56.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view56.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view57.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view57.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view58.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view58.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view59.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view59.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view510.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view510.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view511.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view511.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view512.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view512.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                view61.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view61.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view62.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view62.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view63.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view63.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view64.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view64.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view65.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view65.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view66.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view66.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view67.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view67.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view68.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view68.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view69.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view69.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view610.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view610.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view611.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view611.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view612.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view612.getText(), Toast.LENGTH_SHORT).show();
                    }
                });

                view71.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view71.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view72.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view72.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view73.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view73.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view74.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view74.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view75.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view75.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view76.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view76.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view77.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view77.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view78.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view78.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view79.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view79.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view710.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view710.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view711.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view711.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
                view712.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CurriculumMainActivity.this, view712.getText(), Toast.LENGTH_SHORT).show();
                    }
                });


//                if (cursor.moveToFirst()) {
//                    do {
//                        //遍历Cursor对象，取出数据打印
//                        @SuppressLint("Range") String class_name = cursor.getString(cursor.getColumnIndex("class_name"));
//                        @SuppressLint("Range") String classroom = cursor.getString(cursor.getColumnIndex("classroom"));
//                        @SuppressLint("Range") String teacher = cursor.getString(cursor.getColumnIndex("teacher"));
//                        @SuppressLint("Range") int Week_of_class_time = cursor.getInt(cursor.getColumnIndex("Week_of_class_time"));
//                        @SuppressLint("Range") int class_day = cursor.getInt(cursor.getColumnIndex("class_day"));
//                        @SuppressLint("Range") int lecture_time = cursor.getInt(cursor.getColumnIndex("lecture_time"));
//                        if (Array[spinner.getSelectedItemPosition()] == "第一周") {
//                            if (Week_of_class_time == 1 && class_day == 1 && lecture_time == 1) {
//                                TextView textView = findViewById(R.id.class_1_1);
//                                textView.setText(class_name + '\n' + classroom + '\n' + teacher);
//                                textView.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Toast.makeText(CurriculumMainActivity.this, textView.getText(), Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
//                        }
//                    } while (cursor.moveToNext());


            }


        }
    }
}