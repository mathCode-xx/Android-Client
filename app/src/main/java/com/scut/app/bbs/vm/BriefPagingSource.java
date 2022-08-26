package com.scut.app.bbs.vm;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingState;
import androidx.paging.rxjava3.RxPagingSource;

import com.scut.app.bbs.bean.TopicPageData;
import com.scut.app.bbs.model.BbsModel;
import com.scut.app.bbs.bean.Topic;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @author 徐鑫
 */
public class BriefPagingSource extends RxPagingSource<Integer, Topic> {

    private static final String TAG = "BriefPagingSource";
    @NonNull
    private final BbsModel bbsModel;

    public BriefPagingSource(@NonNull BbsModel bbsModel) {
        this.bbsModel = bbsModel;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, Topic> pagingState) {
        Log.d(TAG, "getRefreshKey: ");
        Integer anchorPosition = pagingState.getAnchorPosition();
        if (anchorPosition == null) {
            return null;
        }

        LoadResult.Page<Integer, Topic> anchorPage = pagingState.closestPageToPosition(anchorPosition);
        if (anchorPage == null) {
            return null;
        }

        Integer prevKey = anchorPage.getPrevKey();
        if (prevKey != null) {
            return prevKey + 1;
        }

        Integer nextKey = anchorPage.getNextKey();
        if (nextKey != null) {
            return nextKey - 1;
        }
        return null;
    }

    @NonNull
    @Override
    public Single<LoadResult<Integer, Topic>> loadSingle(@NonNull LoadParams<Integer> loadParams) {
        Log.d(TAG, "loadSingle: ");
        // Start refresh at page 1 if undefined.
        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
        }

        Single<LoadResult<Integer, Topic>> map = bbsModel.getTopicList(nextPageNumber)
                .subscribeOn(Schedulers.io())
                .map(this::toLoadResult);

        return map.onErrorReturn(LoadResult.Error::new);
    }

    private LoadResult<Integer, Topic> toLoadResult(@NonNull TopicPageData response) {
        Log.d(TAG, "toLoadResult: " + response);
        Integer preKey = response.data.info.isFirstPage ? null : response.data.info.prePage;
        Integer nextKey = response.data.info.isLastPage ? null : response.data.info.nextPage;
        return new LoadResult.Page<>(response.data.info.list,
                preKey,
                nextKey,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }
}
