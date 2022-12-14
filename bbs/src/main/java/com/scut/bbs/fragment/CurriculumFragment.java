package com.scut.bbs.fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.bbs.R;
import com.scut.bbs.databinding.FragmentCurriculumBinding;
import com.scut.bbs.fragment.vm.CurriculumViewModel;

/**
 * 课表界面
 * @author 徐鑫
 */
public class CurriculumFragment extends Fragment {

    private FragmentCurriculumBinding binding;
    private CurriculumViewModel mViewModel;

    public static CurriculumFragment newInstance() {
        return new CurriculumFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCurriculumBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CurriculumViewModel.class);
    }
}