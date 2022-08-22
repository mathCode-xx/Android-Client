package com.scut.app.mine.info.fragment;

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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.scut.app.bbs.bean.Topic;
import com.scut.app.databinding.FragmentMyTopicBinding;
import com.scut.app.mine.info.adapter.MyTopicAdapter;
import com.scut.app.mine.info.vm.MyBbsViewModel;

import java.util.List;

/**
 * 我的主贴
 *
 * @author 徐鑫
 */
public class MyTopicFragment extends Fragment {
    private static final String TAG = "MyTopicFragment";

    FragmentMyTopicBinding binding;
    private ItemTouchHelper.Callback callback;
    private ItemTouchHelper mItemTouchHelper;

    public MyTopicFragment() {
        // Required empty public constructor
    }

    public static MyTopicFragment newInstance() {
        return new MyTopicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyTopicBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        MyBbsViewModel mViewModel = new ViewModelProvider(this).get(MyBbsViewModel.class);
        MyTopicAdapter adapter = new MyTopicAdapter(mViewModel);
        mViewModel.requireMyTopic();
        mViewModel.getMyTopics().observe(getViewLifecycleOwner(), new Observer<List<Topic>>() {
            @Override
            public void onChanged(List<Topic> topics) {
                adapter.setTopicList(topics);
                binding.rcMyTopic.setAdapter(adapter);
            }
        });

        binding.rcMyTopic.setAdapter(adapter);
        binding.rcMyTopic.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}