package com.scut.app.mine.info.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.scut.app.databinding.FragmentMyCommentBinding;

/**
 * 我的评论
 * @author 徐鑫
 */
public class MyCommentFragment extends Fragment {

    FragmentMyCommentBinding binding;

    public MyCommentFragment() {
        // Required empty public constructor
    }
    public static MyCommentFragment newInstance() {
        return new MyCommentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyCommentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}