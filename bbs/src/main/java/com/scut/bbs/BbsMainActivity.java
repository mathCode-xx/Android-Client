package com.scut.bbs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.scut.bbs.login.LoginActivity;
import com.scut.bbs.personal.CenterActivity;
import com.scut.bbs.util.ToastUtils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 测试主界面
 * @author 徐鑫
 */
public class BbsMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_main);

        findViewById(R.id.btnMain).setOnClickListener(v -> {
            Intent intent = new Intent(BbsMainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}