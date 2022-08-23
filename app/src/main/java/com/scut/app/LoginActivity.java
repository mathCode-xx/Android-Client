package com.scut.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.scut.app.databinding.ActivityLoginBinding;
import com.scut.app.util.ToastUtils;


/**
 * 管理登录fragment的activity
 *
 * @author 徐鑫
 */
public class LoginActivity extends AppCompatActivity {

    public static final int LOGIN_SUCCESS = 255;
    public static final int REGISTER_SUCCESS = 256;

    public static final String LOGIN_SUCCESS_MSG = "登录成功";
    public static final String REGISTER_SUCCESS_MSG = "注册成功";

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

    public static class LoginActivityResultContract extends ActivityResultContract<Void, Integer> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Void input) {
            return new Intent(context, LoginActivity.class);
        }

        @Override
        public Integer parseResult(int resultCode, @Nullable Intent intent) {
            switch (resultCode) {
                case LOGIN_SUCCESS:
                    ToastUtils.show(LOGIN_SUCCESS_MSG);
                    break;
                case REGISTER_SUCCESS:
                    ToastUtils.show(REGISTER_SUCCESS_MSG);
                    break;
                default:
            }
            return resultCode;
        }
    }
}