package com.scut.app.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.scut.app.LoginActivity;
import com.scut.app.MyApplication;
import com.scut.app.databinding.FragmentRegisterBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.login.model.LoginModel;
import com.scut.app.login.vm.LoginViewModel;
import com.scut.app.util.ToastUtils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * 注册页面
 * @author 徐鑫
 */
public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    private LoginViewModel mViewModel;
    private FragmentRegisterBinding binding;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        binding.ivRegisterBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.setVm(mViewModel);

        binding.tvRegisterFinish.setOnClickListener(v -> {
            String ensurePwd = String.valueOf(binding.etPasswordEnsure.getText());
            String pwd = String.valueOf(binding.etPassword.getText());
            if (!ensurePwd.equals(pwd)) {
                Toast.makeText(requireContext(), "两次密码不同，请重新输入", Toast.LENGTH_SHORT).show();
            } else {
                mViewModel.register(new LoginModel.CallBackRegister() {
                    @Override
                    public void success(ResponseData responseData) {
                        MyApplication app = MyApplication.getInstance();
                        app.putObj(MyApplication.USER_KEY, mViewModel.getUser());
                        requireActivity().setResult(LoginActivity.REGISTER_SUCCESS);
                        JSONObject data = JSONUtil.parseObj(responseData.getData());
                        app.put(MyApplication.TOKEN_KEY, data.getStr("token"));
                        Log.d(TAG, "success: " + data.getStr("token"));
                        requireActivity().finish();
                    }

                    @Override
                    public void fail(String message) {
                        ToastUtils.show(message);
                    }
                });
            }
        });
    }
}