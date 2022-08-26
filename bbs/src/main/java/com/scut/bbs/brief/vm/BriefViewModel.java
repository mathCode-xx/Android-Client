package com.scut.bbs.brief.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.scut.bbs.brief.model.BriefModel;
import com.scut.bbs.entity.Topic;

/**
 * vm
 *
 * @author 徐鑫
 */
public class BriefViewModel extends AndroidViewModel {


    public BriefViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<PagingData<Topic>> getLiveData() {
        Pager<Integer, Topic> pager = new Pager<>(new PagingConfig(20),
                () -> new BriefPagingSource(new BriefModel()));
        return PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager),this);
    }
}
