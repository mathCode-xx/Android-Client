package com.scut.app.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.scut.app.LoginActivity;
import com.scut.app.MyApplication;
import com.scut.app.R;
import com.scut.app.databinding.FragmentMineBinding;
import com.scut.app.mine.vm.MineViewModel;

/**
 * 个人中心界面
 *
 * @author 徐鑫
 */
public class MineFragment extends Fragment {

    private static final String TAG = "MineFragment";

    private FragmentMineBinding binding;
    private MineViewModel mViewModel;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        if (MyApplication.getInstance().haveLogin()) {
            binding.clMineInfo.setVisibility(View.VISIBLE);
            binding.tvClickLogin.setVisibility(View.GONE);
        } else {
            binding.tvClickLogin.setOnClickListener(v -> {
                // TODO: 2022/8/14 跳转到登录页面
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                requireActivity().startActivity(intent);
            });
        }
    }
}