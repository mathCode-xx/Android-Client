package com.scut.bbs.brief;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.bbs.R;
import com.scut.bbs.brief.vm.BriefAdapter;
import com.scut.bbs.brief.vm.BriefViewModel;
import com.scut.bbs.databinding.ActivityBriefBinding;
import com.scut.bbs.personal.CenterActivity;

/**
 * topic简要列表
 *
 * @author 徐鑫
 */
public class BriefActivity extends AppCompatActivity {

    RecyclerView rvBrief;
    BriefViewModel briefViewModel;
    BriefAdapter adapter;
    ActivityBriefBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_brief);
        briefViewModel = new ViewModelProvider(this).get(BriefViewModel.class);
        rvBrief = findViewById(R.id.rvBrief);

        adapter = new BriefAdapter(new BriefAdapter.BriefComparator(), this);
        rvBrief.setLayoutManager(new LinearLayoutManager(this));
        rvBrief.setAdapter(adapter);

        briefViewModel.getLiveData().observe(this,
                pagingData -> adapter.submitData(getLifecycle(), pagingData));


        binding.ibPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BriefActivity.this, CenterActivity.class);
                startActivity(intent);
            }
        });
    }
}