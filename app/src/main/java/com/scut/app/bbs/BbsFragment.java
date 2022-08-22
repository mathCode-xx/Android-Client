package com.scut.app.bbs;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.paging.LoadState;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scut.app.LoginActivity;
import com.scut.app.MainActivity;
import com.scut.app.MyApplication;
import com.scut.app.R;
import com.scut.app.bbs.adapter.BriefAdapter;
import com.scut.app.bbs.vm.BbsViewModel;
import com.scut.app.databinding.FragmentBbsBinding;
import com.scut.app.util.ToastUtils;

/**
 * 论坛界面
 *
 * @author 徐鑫
 */
public class BbsFragment extends Fragment {

    private static final String TAG = "BbsFragment";

    private FragmentBbsBinding binding;
    private BriefAdapter adapter;
    BbsViewModel mViewModel;

    private ActivityResultLauncher<Void> launcher;


    public static BbsFragment newInstance() {
        return new BbsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcher = registerForActivityResult(new LoginActivity.LoginActivityResultContract(), new ActivityResultCallback<String>() {
            @Override
            public void onActivityResult(String result) {
                ToastUtils.show(result);
            }
        });
        adapter = new BriefAdapter((MainActivity) requireActivity());
        mViewModel = new ViewModelProvider(this).get(BbsViewModel.class);
        //给适配器添加一个监听器，用来监听Paging的工作状态
        adapter.addLoadStateListener(combinedLoadStates -> {
            if (combinedLoadStates.getRefresh() instanceof LoadState.Loading) {

            } else if (combinedLoadStates.getRefresh() instanceof LoadState.Error) {
                if (binding.srlBrief.isRefreshing()) {
                    binding.srlBrief.setRefreshing(false);
                }
                Toast.makeText(requireContext(), "网络异常", Toast.LENGTH_SHORT).show();
            } else if (combinedLoadStates.getRefresh() instanceof LoadState.NotLoading) {
                if (binding.srlBrief.isRefreshing()) {
                    binding.srlBrief.setRefreshing(false);
                }
            }
            return null;
        });

        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = FragmentBbsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");

        MainActivity activity = (MainActivity) requireActivity();
        activity.showBottom();

        binding.rvBrief.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBrief.setAdapter(adapter);

        binding.srlBrief.setRefreshing(true);
        binding.srlBrief.setOnRefreshListener(adapter::refresh);

        mViewModel.getLiveData().observe(getViewLifecycleOwner(), pagingData -> {
            adapter.submitData(getLifecycle(), pagingData);
        });

        //切换到发布帖子界面
        binding.ibReleastTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getInstance().haveLogin()) {
                    MainActivity fragmentActivity = (MainActivity) requireActivity();
                    fragmentActivity.hiddenBottom();
                    Navigation.findNavController(v).navigate(R.id.action_bbsFragment_to_releaseTopicFragment);
                } else {
                    launcher.launch(null);
                }
            }
        });
    }
}