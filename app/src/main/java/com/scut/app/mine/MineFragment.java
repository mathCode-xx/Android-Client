package com.scut.app.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.scut.app.LoginActivity;
import com.scut.app.MyApplication;
import com.scut.app.databinding.FragmentMineBinding;
import com.scut.app.entity.User;
import com.scut.app.mine.info.MyBbsActivity;
import com.scut.app.mine.info.fragment.MyTopicFragment;
import com.scut.app.mine.manager.ManagerActivity;
import com.scut.app.mine.vm.MineViewModel;
import com.scut.app.util.ToastUtils;

/**
 * 个人中心界面
 *
 * @author 徐鑫
 */
public class MineFragment extends Fragment {

    private static final String TAG = "MineFragment";

    private FragmentMineBinding binding;
    private ActivityResultLauncher<Void> launcher;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcher = registerForActivityResult(new LoginActivity.LoginActivityResultContract(), new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String result) {
                ToastUtils.show(result);
                refreshUserInfo();
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
        MineViewModel mViewModel = new ViewModelProvider(this).get(MineViewModel.class);
        if (MyApplication.getInstance().haveLogin()) {
            refreshUserInfo();
        } else {
            binding.tvClickLogin.setOnClickListener(v -> {
                // TODO: 2022/8/14 跳转到登录页面
                launcher.launch(null);
            });
        }

        binding.ibMyDiscuss.setOnClickListener(v -> startMyBbsActivity(0));
        binding.clMyBbs.setOnClickListener(v -> startMyBbsActivity(0));
        binding.ibMyComment.setOnClickListener(v -> startMyBbsActivity(1));
        binding.ibMyCollection.setOnClickListener(v -> startMyBbsActivity(2));

        binding.cvManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!MyApplication.getInstance().haveLogin()) {
                    launcher.launch(null);
                    return;
                }
                ManagerActivity.startActivity(requireContext());
            }
        });
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

    void refreshUserInfo() {
        if (!MyApplication.getInstance().haveLogin()) {
            return;
        }
        binding.clMineInfo.setVisibility(View.VISIBLE);
        binding.tvClickLogin.setVisibility(View.GONE);
        User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);
        binding.tvUserName.setText(user.name);
        binding.tvUid.setText(user.id);
        int permission = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class).permission;
        if (permission == User.MANAGER || permission == User.SYSTEM_MANAGER) {
            binding.cvManager.setVisibility(View.VISIBLE);
        }
    }
}