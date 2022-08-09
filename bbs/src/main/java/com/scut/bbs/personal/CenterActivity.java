package com.scut.bbs.personal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.scut.bbs.MyApplication;
import com.scut.bbs.R;
import com.scut.bbs.databinding.ActivityCenterBinding;
import com.scut.bbs.entity.User;

/**
 * 个人中心
 * @author 徐鑫
 * TODO: 2022/8/6 粉丝数、关注数、头像未实现
 */
public class CenterActivity extends AppCompatActivity {

    private ActivityCenterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_center);
        //设置数据
        binding.setUser(MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class));
    }
}