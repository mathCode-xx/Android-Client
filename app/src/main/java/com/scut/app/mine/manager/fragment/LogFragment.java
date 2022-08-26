package com.scut.app.mine.manager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.scut.app.databinding.FragmentLogBinding;
import com.scut.app.entity.OptLogDTO;
import com.scut.app.mine.manager.adapter.LogAdapter;
import com.scut.app.mine.manager.vm.ManagerViewModel;

import java.util.List;

public class LogFragment extends Fragment {

    private FragmentLogBinding binding;
    private LogAdapter adapter;
    ManagerViewModel mViewModel;

    public LogFragment() {
        // Required empty public constructor
    }

    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new LogAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initWidget();
        initViewModel();

    }

    private void initWidget() {
        binding.rvLog.setAdapter(adapter);
        binding.rvLog.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.srlLog.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.requireLog();
            }
        });
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(ManagerViewModel.class);

        mViewModel.getLogList().observe(getViewLifecycleOwner(), logs -> {
            adapter.submitList(logs);
            if (binding.srlLog.isRefreshing()) {
                binding.srlLog.setRefreshing(false);
            }
        });
        mViewModel.requireLog();
    }
}