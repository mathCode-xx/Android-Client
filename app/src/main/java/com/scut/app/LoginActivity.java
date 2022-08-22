package com.scut.app;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.scut.app.databinding.ActivityLoginBinding;


/**
 * 管理登录fragment的activity
 * @author 徐鑫
 */
public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN_SUCCESS = 255;
    public static final int REGISTER_SUCCESS = 256;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class LoginActivityResultContract extends ActivityResultContract<Void, String> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Void input) {
            return new Intent(context, LoginActivity.class);
        }
        @Override
        public String parseResult(int resultCode, @Nullable Intent intent) {
            if (resultCode == LOGIN_SUCCESS) {
                return "登录成功";
            } else if (resultCode == REGISTER_SUCCESS) {
                return "注册成功";
            }
            return "操作失败，请重试";
        }
    }
}