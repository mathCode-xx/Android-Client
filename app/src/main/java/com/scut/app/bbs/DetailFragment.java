package com.scut.app.bbs;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scut.app.LoginActivity;
import com.scut.app.MyApplication;
import com.scut.app.bbs.adapter.CommentAdapter;
import com.scut.app.bbs.vm.TopicViewModel;
import com.scut.app.databinding.FragmentDetailBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

/**
 * 帖子详情页
 *
 * @author 徐鑫
 */
public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private long topicId;
    TopicViewModel mViewModel;
    CommentAdapter adapter = new CommentAdapter();
    private ActivityResultLauncher<Void> launcher;

    public DetailFragment() {
    }

    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            topicId = getArguments().getLong("topic");
        }
        launcher = registerForActivityResult(new LoginActivity.LoginActivityResultContract(), result -> {
            switch (result) {
                case LoginActivity.LOGIN_SUCCESS:
                case LoginActivity.REGISTER_SUCCESS:
                    loadAfterLogin();
                    break;
                default:
            }
        });
        mViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        binding.setVm(mViewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();

        if (MyApplication.getInstance().haveLogin()) {
            loadAfterLogin();
        }
    }

    /**
     * 登录之后的加载
     */
    private void loadAfterLogin() {
        binding.etComment.setVisibility(View.VISIBLE);
        binding.btnComment.setText("回复");
        binding.btnComment.setOnClickListener(v -> mViewModel.comment(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                ToastUtils.show(responseData.getMessage());
                binding.etComment.setText("");
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        }));
    }

    /**
     * 初始化页面（未登录状态）
     */
    private void init() {
        //显示弹窗
        ProgressDialog dialog = new ProgressDialog(requireContext());
        dialog.setTitle("正在加载...");
        dialog.show();

        //设置返回按钮的监听事件
        binding.ivBcak.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        //初始化RecycleView
        binding.rvComment.setAdapter(adapter);
        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getCommentList().observe(getViewLifecycleOwner(), adapter::submitList);

        //请求数据
        mViewModel.requestTopic(this.topicId);
        mViewModel.requireComment(this.topicId);

        //下拉刷新评论区
        binding.srlComment.setOnRefreshListener(() -> mViewModel.requireComment(DetailFragment.this.topicId));

        mViewModel.getTopic().observe(getViewLifecycleOwner(), topic -> {
            dialog.dismiss();
            binding.tvUserId.setText(topic.userId);
            binding.tvReleaseTime.setText(topic.time);
            binding.tvTopicContent.setText(topic.content);
        });

        mViewModel.getCommentList().observe(getViewLifecycleOwner(), comments -> {
            adapter.submitList(comments);
            if (binding.srlComment.isRefreshing()) {
                binding.srlComment.setRefreshing(false);
            }
        });

        //隐藏评论功能，显示一个登录按钮
        binding.btnComment.setText("登录");
        binding.etComment.setVisibility(View.GONE);
        binding.btnComment.setOnClickListener(v -> launcher.launch(null));
    }
}