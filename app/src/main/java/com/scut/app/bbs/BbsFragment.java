package com.scut.app.bbs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.scut.app.bbs.adapter.BriefAdapter;
import com.scut.app.databinding.FragmentBbsBinding;
import com.scut.app.bbs.vm.BbsViewModel;
import com.scut.app.util.ToastUtils;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * 论坛界面
 * @author 徐鑫
 */
public class BbsFragment extends Fragment {

    private static final String TAG = "BbsFragment";

    private FragmentBbsBinding binding;
    private BriefAdapter adapter;


    public static BbsFragment newInstance() {
        return new BbsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBbsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BbsViewModel mViewModel = new ViewModelProvider(this).get(BbsViewModel.class);

        adapter = new BriefAdapter();

        binding.rvBrief.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBrief.setAdapter(adapter);

        binding.srlBrief.setRefreshing(true);
        binding.srlBrief.setOnRefreshListener(() -> adapter.refresh());

        adapter.addLoadStateListener(new Function1<CombinedLoadStates, Unit>() {
            @Override
            public Unit invoke(CombinedLoadStates combinedLoadStates) {
                if (combinedLoadStates.getRefresh() instanceof LoadState.Loading) {
                    Log.d(TAG, "invoke: 正在加载");
                } else if (combinedLoadStates.getRefresh() instanceof LoadState.Error) {
                    if (binding.srlBrief.isRefreshing()) {
                        binding.srlBrief.setRefreshing(false);
                    }
                    Log.d(TAG, "invoke: 加载异常");
                    Toast.makeText(requireContext(), "网络异常", Toast.LENGTH_SHORT).show();
                } else if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading) {
                    Log.d(TAG, "invoke: 加载完成");
                    if (binding.srlBrief.isRefreshing()) {
                        binding.srlBrief.setRefreshing(false);
                    }
                }
                return null;
            }
        });

        mViewModel.getLiveData().observe(getViewLifecycleOwner(), pagingData -> {
            adapter.submitData(getLifecycle(), pagingData);
        });

    }
}