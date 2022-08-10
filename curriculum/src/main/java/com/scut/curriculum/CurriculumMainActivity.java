package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class CurriculumMainActivity extends AppCompatActivity {
    private ImageButton findDevices;
    public static List<Activity> activityList = new LinkedList();

    //内容数组
    String[] Array = {"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周", "第九周", "第十周", "第十一周",
            "第十二周", "第十三周", "第十四周", "第十五周", "第十六周", "第十七周", "第十八周", "第十九周", "第二十周"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curriculum_main);

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

            }
        }
    }
}