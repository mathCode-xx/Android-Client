package com.scut.curriculum;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NewsContentActivity extends AppCompatActivity {
    private ImageButton findDevices;
    public static void actionStart(Context context,String newsTitle,String newsContent){
        Intent intent = new Intent(context,NewsContentActivity.class);
        intent.putExtra("news_title",newsTitle);
        intent.putExtra("news_content",newsContent);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);

        String newsTitle = getIntent().getStringExtra("news_title");//获取传入的新闻标题
        String newsContent = getIntent().getStringExtra("news_content");

        NewsContentFragment newsContentFragment = (NewsContentFragment) getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle,newsContent);//刷新界面


        findDevices = findViewById(R.id.ic_back);
        findDevices.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();  //直接关闭当前页面
            }
        });

    }


}