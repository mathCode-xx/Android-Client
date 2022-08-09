package com.scut.bbs.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.scut.bbs.BR;
import com.scut.bbs.R;
import com.scut.bbs.databinding.ActivityLoginBinding;
import com.scut.bbs.login.vm.LoginViewModel;

/**
 * 登录页面
 *
 * @author 徐鑫
 */
public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding.setVariable(BR.loginViewModel, loginViewModel);
        // 这里的参数类型为LifecycleOwner，把自己的生命周期告诉给已绑定对象的布局
        binding.setLifecycleOwner(this);

        loginViewModel.getSuccess().observe(this, aBoolean -> {
            if (aBoolean) {
                finish();
            }
        });
    }
}