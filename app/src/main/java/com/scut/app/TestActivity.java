package com.scut.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.scut.app.bbs.bean.TopicPageData;
import com.scut.app.bbs.model.BbsModel;
import com.scut.app.net.ITopicServer;
import com.scut.app.util.NetUtils;

import cn.hutool.json.JSONUtil;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Single<TopicPageData> topicByPage = NetUtils.createRetrofit(ITopicServer.class).getTopicByPage(1, 2);
                topicByPage.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<TopicPageData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Toast.makeText(TestActivity.this, "发布订阅", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(@NonNull TopicPageData topicPageData) {
                        Log.d(TAG, "onSuccess: " + JSONUtil.toJsonStr(topicPageData));
                        Toast.makeText(TestActivity.this, "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(TestActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onError: " + e.getCause() + "\n" + e.getMessage());
                    }
                });
            }
        });
    }
}