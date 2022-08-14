package com.scut.bbs.login.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.bbs.BR;
import com.scut.bbs.R;
import com.scut.bbs.databinding.ActivityLoginBinding;
import com.scut.bbs.databinding.FragmentLoginBinding;
import com.scut.bbs.login.vm.LoginViewModel;

/**
 * 登录界面
 * @author 徐鑫
 */
public class LoginFragment extends Fragment {

    LoginViewModel loginViewModel;
    FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_login, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.setVariable(BR.loginViewModel, loginViewModel);
        // 这里的参数类型为LifecycleOwner，把自己的生命周期告诉给已绑定对象的布局
        binding.setLifecycleOwner(this);

        binding.btnLogin.setOnClickListener(v -> loginViewModel.login());

        //跳转到注册页面
        binding.tvRegister.setOnClickListener(v -> Navigation.findNavController(binding.tvRegister)
                .navigate(R.id.action_loginFragment_to_registerFragment));
        //跳转到找回密码页面
        binding.tvForget.setOnClickListener(v -> Navigation.findNavController(binding.tvForget)
                .navigate(R.id.action_loginFragment_to_retrieveFragment));

    }
}