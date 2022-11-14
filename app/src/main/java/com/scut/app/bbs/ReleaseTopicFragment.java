package com.scut.app.bbs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.scut.app.bbs.vm.ReleaseViewModel;
import com.scut.app.databinding.FragmentReleaseTopicBinding;
import com.scut.app.entity.ResponseData;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

/**
 * 发布帖子
 *
 * @author 徐鑫
 */
public class ReleaseTopicFragment extends Fragment {

    private FragmentReleaseTopicBinding binding;
    private ReleaseViewModel mViewModel;

    public ReleaseTopicFragment() {
        // Required empty public constructor
    }

    public static ReleaseTopicFragment newInstance() {
        return new ReleaseTopicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReleaseViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReleaseTopicBinding.inflate(inflater, container, false);
        binding.setVm(mViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.ivBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        binding.tvRelease.setOnClickListener(v -> mViewModel.release(new CallBack() {
            @Override
            public void success(ResponseData responseData) {
                ToastUtils.show(responseData.getErrMessage());
                Navigation.findNavController(v).popBackStack();
            }

            @Override
            public void fail(String message) {
                ToastUtils.show(message);
            }
        }));
    }
}