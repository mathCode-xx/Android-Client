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

import com.scut.app.databinding.FragmentAuditBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.mine.manager.adapter.AuditAdapter;
import com.scut.app.mine.manager.vm.ManagerViewModel;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

public class AuditFragment extends Fragment {
    private static final String TAG = "AuditFragment";

    FragmentAuditBinding binding;
    ManagerViewModel mViewModel;
    AuditAdapter adapter;

    public AuditFragment() {
        // Required empty public constructor
    }

    public static AuditFragment newInstance() {
        return new AuditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuditBinding.inflate(inflater, container, false);

        //设置适配器中item长按的回调
        adapter = new AuditAdapter(() -> {
            binding.layoutBottom.setVisibility(View.VISIBLE);
            binding.cbSelectAll.setOnClickListener(v -> {
                if (binding.cbSelectAll.isChecked()) {
                    adapter.selectAll();
                } else {
                    adapter.cancelSelect();
                }
            });
            binding.btnCommit.setOnClickListener(v -> mViewModel.commitAudit(adapter.getCommitUserId(), new CallBack() {
                @Override
                public void success(ResponseData responseData) {
                    ToastUtils.show(responseData.getMessage());
                    adapter.deleteItem();
                    binding.layoutBottom.setVisibility(View.GONE);
                }

                @Override
                public void fail(String message) {
                    ToastUtils.show(message);
                }
            }));
        });
        Log.d(TAG, "onCreateView: " + adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(ManagerViewModel.class);
        //请求数据
        mViewModel.requireNeedToAudit();
        //开始下拉刷新动画
        binding.srlAudit.setRefreshing(true);
        binding.srlAudit.setOnRefreshListener(() -> mViewModel.requireNeedToAudit());

        binding.rvAudit.setAdapter(adapter);
        binding.rvAudit.setLayoutManager(new LinearLayoutManager(requireContext()));

        mViewModel.getNeedToAuditList().observe(getViewLifecycleOwner(), users -> {
            //请求到了数据之后，刷新adapter
            adapter.setUserList(users);
            binding.rvAudit.setAdapter(adapter);

            if (binding.srlAudit.isRefreshing()) {
                //关闭下拉刷新动画
                binding.srlAudit.setRefreshing(false);
            }
        });
    }
}