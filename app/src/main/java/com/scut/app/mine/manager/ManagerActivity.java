package com.scut.app.mine.manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.scut.app.R;
import com.scut.app.databinding.ActivityManagerBinding;
import com.scut.app.mine.manager.fragment.AuditFragment;
import com.scut.app.mine.manager.fragment.FinishFragment;

public class ManagerActivity extends AppCompatActivity {

    ActivityManagerBinding binding;

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
                    case 1:
                        return FinishFragment.newInstance();
                    default:
                        return AuditFragment.newInstance();
                }
            }

            @Override
            public int getItemCount() {
                return 2;
            }
        });

        new TabLayoutMediator(binding.tlManger, binding.vpManager, (tab, position) -> {
            switch (position) {
                case 1:
                    tab.setText("已完成");
                    break;
                default:
                    tab.setText("待审核");
                    break;
            }
        }).attach();
    }
}