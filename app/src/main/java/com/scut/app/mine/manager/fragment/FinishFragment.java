package com.scut.app.mine.manager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scut.app.databinding.FragmentFinishBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.mine.manager.adapter.AuditAdapter;
import com.scut.app.mine.manager.vm.ManagerViewModel;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

import java.util.List;

public class FinishFragment extends Fragment {

    private static final String TAG = "AuditFragment";

    private ManagerViewModel mViewModel;
    private AuditAdapter adapter;
    private FragmentFinishBinding binding;

    public FinishFragment() {
    }


    public static FinishFragment newInstance() {
        return new FinishFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFinishBinding.inflate(inflater, container, false);
        //设置适配器中item被长按之后的回调
        adapter = new AuditAdapter(() -> {
            //显示底部控件
            binding.bottomLayout.setVisibility(View.VISIBLE);
            binding.cbSelectAll.setOnClickListener(v -> {
                if (binding.cbSelectAll.isChecked()) {
                    adapter.selectAll();
                } else {
                    adapter.cancelSelect();
                }
            });
            binding.btnRollBack.setOnClickListener(v -> {
                List<String> stringList = adapter.getCommitUserId();
                mViewModel.commitRollback(stringList, new CallBack() {
                    @Override
                    public void success(ResponseData responseData) {
                        ToastUtils.show(responseData.getErrMessage());
                        adapter.deleteItem();
                        binding.bottomLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void fail(String message) {
                        ToastUtils.show(message);
                    }
                });
            });
        });
        Log.d(TAG, "onCreateView: " + adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(ManagerViewModel.class);

        //请求数据
        mViewModel.requireFinishAudit();

        //打开下拉刷新
        binding.srlFinish.setRefreshing(true);
        binding.srlFinish.setOnRefreshListener(() -> mViewModel.requireFinishAudit());

        binding.rvFinishAudit.setAdapter(adapter);
        binding.rvFinishAudit.setLayoutManager(new LinearLayoutManager(requireContext()));

        mViewModel.getFinishAuditList().observe(getViewLifecycleOwner(), users -> {
            //请求到了数据之后，刷新adapter
            adapter.setUserList(users);
            binding.rvFinishAudit.setAdapter(adapter);
            //关闭下拉刷新的动画
            if (binding.srlFinish.isRefreshing()) {
                binding.srlFinish.setRefreshing(false);
            }
        });
    }
}