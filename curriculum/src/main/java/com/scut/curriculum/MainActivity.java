package com.scut.curriculum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class MainActivity extends AppCompatActivity {
    public static List<Activity> activityList = new LinkedList();

    //内容数组
    String[] Array = {"第一周", "第二周", "第三周", "第四周", "第五周", "第六周", "第七周", "第八周", "第九周", "第十周", "第十一周",
            "第十二周", "第十三周", "第十四周", "第十五周", "第十六周", "第十七周", "第十八周", "第十九周", "第二十周"};
    private ImageButton findDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/**
 * 下面是最下方按钮的转移页面方式
 *  本来也可以使用navigation正好达到同样的目的，但是显然不太会用fragment
 *  怎么设置之类的，还是直接用四个Activity比较简单一点
 */
        ImageButton button2 = findViewById(R.id.black_market);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        ImageButton button3 = findViewById(R.id.news_1);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
            }
        });

        ImageButton button4 = findViewById(R.id.setting);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
            }
        });

/**
 * 下面是将toolbar设置为actionbar
 */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        findDevices = findViewById(R.id.ic_back);
        findDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  //直接关闭当前页面
            }
        });
/**
 * 下拉页面的设置
 */
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

        MainActivity.activityList.add(this);
    }

    class MyonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.accept) {
                //获取选中项
                Spinner spinner = findViewById(R.id.spinner);
                //这里要留着修改课表的项目
                Toast.makeText(MainActivity.this, Array[spinner.getSelectedItemPosition()], Toast.LENGTH_SHORT).show();

            }
        }
    }


}