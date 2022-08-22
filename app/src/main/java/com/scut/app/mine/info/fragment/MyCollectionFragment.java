package com.scut.app.mine.info.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.scut.app.databinding.FragmentMyCollectionBinding;

/**
 * 我的收藏
 *
 * @author 徐鑫
 */
public class MyCollectionFragment extends Fragment {

    FragmentMyCollectionBinding binding;


    public MyCollectionFragment() {
        // Required empty public constructor
    }

    public static MyCollectionFragment newInstance() {
        return new MyCollectionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyCollectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }
}