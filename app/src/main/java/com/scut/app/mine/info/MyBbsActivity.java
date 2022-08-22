package com.scut.app.mine.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.scut.app.databinding.ActivityMyBbsBinding;
import com.scut.app.mine.info.fragment.MyCollectionFragment;
import com.scut.app.mine.info.fragment.MyCommentFragment;
import com.scut.app.mine.info.fragment.MyTopicFragment;

/**
 * 管理我的论坛
 * ViewPage2 + TabLayout
 *
 * @author 徐鑫
 */
public class MyBbsActivity extends AppCompatActivity {

    ActivityMyBbsBinding binding;
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBbsBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        int viewPage = 1;
        if (intent != null) {
            viewPage = intent.getIntExtra("viewPage", viewPage);
        }

        setContentView(binding.getRoot());
        binding.ivBack.setOnClickListener(v -> finish());

        viewPager2 = binding.vp2Bbs;

        viewPager2.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                switch (position) {
                    case 0:
                        return MyTopicFragment.newInstance();
                    case 1:
                        return MyCommentFragment.newInstance();
                    default:
                        return MyCollectionFragment.newInstance();
                }
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        });
        viewPager2.setCurrentItem(viewPage);

        tabLayout = binding.tlBbs;

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("我的讨论");
                    break;
                case 1:
                    tab.setText("我的回复");
                    break;
                default:
                    tab.setText("我的收藏");
            }
        })).attach();

    }
}