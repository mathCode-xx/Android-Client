package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Setting extends AppCompatActivity {
    private ImageButton findDevices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Button exit = findViewById(R.id.exit_);
        exit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                exit();
            }
        });


        Button button1 = findViewById(R.id.first);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, CurriculumMainActivity.class);
                startActivity(intent);
            }
        });

        Button button2 = findViewById(R.id.second);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = findViewById(R.id.third);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this, ThirdActivity.class);
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
        CurriculumMainActivity.activityList.add(this);
    }

    private void exit() {
        for (Activity act : CurriculumMainActivity.activityList) {
            act.finish();
        }
        System.exit(0);
    }

}