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
        // Required empty public constructor
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
        launcher = registerForActivityResult(new LoginActivity.LoginActivityResultContract(), ToastUtils::show);
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

        ProgressDialog dialog = new ProgressDialog(requireContext());
        dialog.setTitle("正在加载...");
        dialog.show();

        binding.rvComment.setAdapter(adapter);
        binding.rvComment.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getCommentList().observe(getViewLifecycleOwner(), adapter::submitList);

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

        binding.btnComment.setOnClickListener(v -> {
            if (!MyApplication.getInstance().haveLogin()) {
                ToastUtils.show("请先登录！");
                launcher.launch(null);
                return;
            }

            mViewModel.comment(new CallBack() {
                @Override
                public void success(ResponseData responseData) {
                    ToastUtils.show(responseData.getMessage());
                    binding.etComment.setText("");
                }

                @Override
                public void fail(String message) {
                    ToastUtils.show(message);
                }
            });
        });

    }
}