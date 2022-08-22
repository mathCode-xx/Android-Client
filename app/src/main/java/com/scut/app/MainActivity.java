package com.scut.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.scut.app.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * 主界面
 *
 * @author 徐鑫
 */
public class MainActivity extends AppCompatActivity {

    private final HashMap<Integer, MotionLayout> map = new HashMap<>();
    ActivityMainBinding binding;
    public static final int MSG_CODE = 0xff;
    private boolean hasHidden = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // 定义组件id与motionLayout的映射，方便调用
        // 如果以后需要添加底部导航组件，只需要在这里加一个映射
        map.put(R.id.mineFragment, binding.bottomNavigationMine.motionMineLayout);
        map.put(R.id.bbsFragment, binding.bottomNavigationBbs.motionBbsLayout);
        map.put(R.id.curriculumFragment, binding.bottomNavigationCurriculum.motionCurriculumLayout);

        // TODO: 2022/8/12 不知道这里为什么只能用这个方法获取navController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainerView);

        if (navHostFragment == null) {
            Toast.makeText(this, "系统出错！", Toast.LENGTH_SHORT).show();
            return;
        }
        NavController navController = navHostFragment.getNavController();

        for (Map.Entry<Integer, MotionLayout> entry :
                map.entrySet()) {
            entry.getValue().setOnClickListener(v -> {
                navController.popBackStack();
                navController.navigate(entry.getKey());
            });
        }

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // 将自己弹出栈
            // controller.popBackStack(); //不能在这里弹出，如果在这里弹出，那么就是一压入栈就将栈顶弹出，会导致这个栈永远为空

            for (MotionLayout motionLayout :
                    map.values()) {
                motionLayout.setProgress(0.1f);
            }
            // 当前点击的导航栏id
            int destinationId = destination.getId();
            MotionLayout motionLayout = map.get(destinationId);

            // 遍历所有导航栏，为每个导航栏设置是否可以点击
            // 目标是：当前导航栏不可点击，其他导航栏可以点击
            for (Map.Entry<Integer, MotionLayout> entry :
                    map.entrySet()) {
                entry.getValue().setClickable(entry.getKey() != destinationId);
            }

            if (motionLayout != null) {
                motionLayout.transitionToEnd();
            }
        });
    }

    public void hiddenBottom() {
        if (!this.hasHidden) {
            binding.linearLayout.setVisibility(View.GONE);
            this.hasHidden = true;
        }
    }

    public void showBottom() {
        if (this.hasHidden) {
            binding.linearLayout.setVisibility(View.VISIBLE);
            this.hasHidden = false;
        }
    }
}