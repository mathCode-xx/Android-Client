package com.scut.app.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.scut.app.LoginActivity;
import com.scut.app.MyApplication;
import com.scut.app.databinding.FragmentMineBinding;
import com.scut.app.entity.User;
import com.scut.app.mine.info.MyBbsActivity;
import com.scut.app.mine.manager.ManagerActivity;
import com.scut.app.mine.vm.MineViewModel;

/**
 * 个人中心界面
 *
 * @author 徐鑫
 */
public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private ActivityResultLauncher<Void> launcher;
    MineViewModel mViewModel;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcher = registerForActivityResult(new LoginActivity.LoginActivityResultContract(), result -> {
            switch (result) {
                case LoginActivity.LOGIN_SUCCESS:
                case LoginActivity.REGISTER_SUCCESS:
                    refreshUserInfo();
                    break;
                default:
            }
        });
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

        init();
        if (MyApplication.getInstance().haveLogin()) {
            refreshUserInfo();
        }
    }

    /**
     * 初始化控件（未登录状态）
     */
    private void init() {
        //关闭个人信息显示
        binding.clMineInfo.setVisibility(View.GONE);
        //关闭管理界面显示
        binding.cvManager.setVisibility(View.GONE);
        //关闭退出登录按键
        binding.btnLogout.setVisibility(View.GONE);

        //设置个人博客的进入操作
        binding.ibMyDiscuss.setOnClickListener(v -> startMyBbsActivity(0));
        binding.clMyBbs.setOnClickListener(v -> startMyBbsActivity(0));
        binding.ibMyComment.setOnClickListener(v -> startMyBbsActivity(1));
        binding.ibMyCollection.setOnClickListener(v -> startMyBbsActivity(2));

        //打开登录按键，并设置按键监听事件
        binding.tvClickLogin.setVisibility(View.VISIBLE);
        binding.tvClickLogin.setOnClickListener(v -> launcher.launch(null));
    }

    private void logout() {
        //清空全局变量
        MyApplication.getInstance().clear();

        //还原控件到未登录状态
        init();

        mViewModel.logout();
    }

    void startMyBbsActivity(int page) {
        if (!MyApplication.getInstance().haveLogin()) {
            launcher.launch(null);
            return;
        }
        Intent intent = new Intent(requireContext(), MyBbsActivity.class);
        intent.putExtra("viewPage", page);
        startActivity(intent);
    }

    /**
     * 登录状态刷新页面
     */
    private void refreshUserInfo() {
        //关闭登录按键
        binding.tvClickLogin.setVisibility(View.GONE);
        //显示个人信息
        binding.clMineInfo.setVisibility(View.VISIBLE);
        User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);
        binding.tvUserName.setText(user.name);
        binding.tvUid.setText(user.id);

        // 显示退出登录按键
        binding.btnLogout.setVisibility(View.VISIBLE);
        //退出登录
        binding.btnLogout.setOnClickListener(v -> logout());

        //根据当前用户的权限决定是否显示管理按键
        int permission = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class).permission;
        if (permission == User.MANAGER || permission == User.SYSTEM_MANAGER) {
            binding.cvManager.setVisibility(View.VISIBLE);

            //普通管理员没有查看日志信息的权限，系统管理员可以
            if (permission == User.MANAGER) {
                binding.ivLog.setVisibility(View.GONE);
                binding.tvLog.setVisibility(View.GONE);
            } else {
                binding.tvLog.setVisibility(View.VISIBLE);
                binding.ivLog.setVisibility(View.VISIBLE);
            }

            binding.cvManager.setOnClickListener(v -> {
                if (!MyApplication.getInstance().haveLogin()) {
                    launcher.launch(null);
                    return;
                }
                ManagerActivity.startActivity(requireContext());
            });
        } else {
            binding.cvManager.setVisibility(View.GONE);
        }
    }
}