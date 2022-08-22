package com.scut.app.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.scut.app.BR;
import com.scut.app.LoginActivity;
import com.scut.app.R;
import com.scut.app.databinding.FragmentLoginBinding;
import com.scut.app.login.model.LoginModel;
import com.scut.app.login.vm.LoginViewModel;
import com.scut.app.util.ToastUtils;

/**
 * 登录页面
 *
 * @author 徐鑫
 */
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.setVariable(BR.loginViewModel, loginViewModel);
        // 这里的参数类型为LifecycleOwner，把自己的生命周期告诉给已绑定对象的布局
        binding.setLifecycleOwner(this);

        //登录按钮
        binding.btnLogin.setOnClickListener(v -> loginViewModel.login(new LoginModel.ILoginDataCallback() {
            @Override
            public void success() {
                requireActivity().setResult(LoginActivity.LOGIN_SUCCESS);
                requireActivity().finish();
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        }));

        // 跳转到注册页面
        binding.tvRegister.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        // 跳转到找回密码页面
        binding.tvForget.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_forgetFragment);
        });
    }
}