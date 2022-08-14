package com.scut.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.scut.app.databinding.ActivityLoginBinding;


/**
 * 管理登录fragment的activity
 * @author 徐鑫
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}