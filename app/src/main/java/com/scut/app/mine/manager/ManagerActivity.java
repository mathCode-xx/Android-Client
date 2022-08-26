package com.scut.app.mine.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.scut.app.MyApplication;
import com.scut.app.databinding.ActivityManagerBinding;
import com.scut.app.entity.User;
import com.scut.app.mine.manager.fragment.AuditFragment;
import com.scut.app.mine.manager.fragment.FinishFragment;
import com.scut.app.mine.manager.fragment.LogFragment;

public class ManagerActivity extends AppCompatActivity {

    ActivityManagerBinding binding;
    User user = MyApplication.getInstance().getObj(MyApplication.USER_KEY, User.class);

    /**
     * 启动ManagerActivity
     * @param context 操作启动的上下文
     */
    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, ManagerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> finish());

        binding.vpManager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return AuditFragment.newInstance();
                    case 1:
                        return FinishFragment.newInstance();
                    default:
                        return LogFragment.newInstance();
                }
            }

            @Override
            public int getItemCount() {
                if (user.permission == User.MANAGER) {
                    return 2;
                } else if (user.permission == User.SYSTEM_MANAGER) {
                    return 3;
                }
                return 0;
            }
        });

        new TabLayoutMediator(binding.tlManger, binding.vpManager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("待审核");
                    break;
                case 1:
                    tab.setText("已完成");
                    break;
                case 2:
                    tab.setText("操作日志");
                    break;
                default:
            }
        }).attach();
    }
}