package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity4 extends AppCompatActivity {

    private ImageButton findDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button exit = findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                exit();
            }
        });


        ImageButton button1 = findViewById(R.id.class_schedule);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ImageButton button2 = findViewById(R.id.black_market);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity4.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        ImageButton button3 = findViewById(R.id.news_1);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findDevices = findViewById(R.id.ic_back);
        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //直接关闭当前页面
            }
        });
        MainActivity.activityList.add(this);
    }

    private void exit() {
        for (Activity act : MainActivity.activityList) {
            act.finish();
        }
        System.exit(0);
    }

}