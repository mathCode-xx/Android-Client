package com.scut.bbs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import com.scut.bbs.databinding.ActivityBbsMainBinding;
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

    private ActivityBbsMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBbsMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}