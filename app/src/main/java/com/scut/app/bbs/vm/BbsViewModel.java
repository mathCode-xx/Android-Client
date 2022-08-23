package com.scut.app.bbs.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.scut.app.bbs.bean.Topic;
import com.scut.app.bbs.model.BbsModel;

public class BbsViewModel extends ViewModel {

    public LiveData<PagingData<Topic>> getLiveData() {
        Pager<Integer, Topic> pager = new Pager<>(new PagingConfig(20),
                () -> new BriefPagingSource(new BbsModel()));
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), this);
    }
}