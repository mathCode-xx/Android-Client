package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeActivity extends AppCompatActivity {
    private ImageButton findDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        TextView add_information = findViewById(R.id.add_class_information);
        add_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, add_class_information_Activity.class);
                startActivity(intent);
            }
        });

        TextView delete_information = findViewById(R.id.delete_class_information);
        delete_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, delete_class_information_Activity.class);
                startActivity(intent);
            }
        });



        TextView change_information = findViewById(R.id.change_class_information);
        change_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, change_class_information_Activity.class);
                startActivity(intent);
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