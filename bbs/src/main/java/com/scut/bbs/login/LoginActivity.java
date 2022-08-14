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

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //binding.fragmentContainerView2.
    }
}