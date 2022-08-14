package com.scut.app.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.app.databinding.FragmentForgetBinding;
import com.scut.app.login.vm.LoginViewModel;

/**
 * 忘记密码页面
 * @author 徐鑫
 */
public class ForgetFragment extends Fragment {

    private FragmentForgetBinding binding;
    private LoginViewModel mViewModel;

    public ForgetFragment() {
        // Required empty public constructor
    }

    public static ForgetFragment newInstance() {
        return new ForgetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentForgetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}