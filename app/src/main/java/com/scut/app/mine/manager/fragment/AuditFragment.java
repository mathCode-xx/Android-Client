package com.scut.app.mine.manager.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scut.app.databinding.FragmentAuditBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.entity.User;
import com.scut.app.mine.manager.adapter.AuditAdapter;
import com.scut.app.mine.manager.vm.ManagerViewModel;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

import java.util.List;

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
        adapter = new AuditAdapter(() -> {
            binding.layoutBottom.setVisibility(View.VISIBLE);
            binding.cbSelectAll.setOnClickListener(v -> {
                if (binding.cbSelectAll.isChecked()) {
                    adapter.selectAll();
                }
            });
            binding.btnCommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewModel.commitAudit(adapter.getCommitUserId(), new CallBack() {
                        @Override
                        public void success(ResponseData responseData) {
                            adapter.deleteItem();
                            binding.layoutBottom.setVisibility(View.GONE);
                        }

                        @Override
                        public void fail(String message) {
                            ToastUtils.show(message);
                        }
                    });
                }
            });
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ManagerViewModel.class);
        mViewModel.requireUser();
        binding.rvAudit.setAdapter(adapter);
        binding.rvAudit.setLayoutManager(new LinearLayoutManager(requireContext()));

        mViewModel.getUserList().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUserList(users);
                binding.rvAudit.setAdapter(adapter);
            }
        });
    }
}