package com.scut.bbs.login.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scut.bbs.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetrieveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetrieveFragment extends Fragment {

    public RetrieveFragment() {
        // Required empty public constructor
    }

    public static RetrieveFragment newInstance(String param1, String param2) {
        return new RetrieveFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retrieve, container, false);
    }
}