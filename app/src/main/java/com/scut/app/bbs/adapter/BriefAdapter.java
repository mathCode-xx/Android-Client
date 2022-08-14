package com.scut.app.bbs.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.app.MyApplication;
import com.scut.app.R;
import com.scut.app.entity.TopicRecord;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.room.dao.TopicRecordDAO;
import com.scut.app.room.database.TopicRecordDatabase;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * topic列表的适配器
 *
 * @author 徐鑫
 */
public class BriefAdapter extends PagingDataAdapter<Topic, BriefAdapter.BriefViewHolder> {

    private static final String TAG = "BriefAdapter";

    public BriefAdapter() {
        super(new BriefComparator());
    }

    @NonNull
    @Override
    public BriefViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_brief, parent, false);
        return new BriefViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BriefViewHolder holder, int position) {
        Topic topic = getItem(position);
        if (topic == null) {
            return;
        }
        Log.d(TAG, "onBindViewHolder: " + topic.hashCode());
        holder.ibLike.setOnClickListener(v -> {

        });
        holder.ibCollection.setOnClickListener(v -> {

        });
        holder.tvUserId.setText(topic.userId);
        holder.tvContent.setText(topic.content);
        holder.tvLikeCount.setText(String.valueOf(topic.likeCount));
        holder.tvCommentCount.setText(String.valueOf(topic.commentCount));
        holder.tvCollectionCount.setText(String.valueOf(topic.collectionCount));

        holder.itemView.setOnClickListener(v -> {
            if (MyApplication.getInstance().haveLogin()) {
                //已经登录了，直接跳转到详情页面
                // TODO: 2022/8/12 记得要带参数过去
                Navigation.findNavController(v).navigate(R.id.action_bbsFragment_to_detailFragment);
            } else {
                //没有登陆则跳转到登录页面

            }
        });
    }

    private void saveData(TopicRecord topicRecord) {
        TopicRecordDAO topicRecordDAO = TopicRecordDatabase.getInstance().topicRecordDAO();

        Single<Integer> update = topicRecordDAO.update(topicRecord);

        update.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Integer integer) {
                        Log.d(TAG, "onSuccess: topic id为" + topicRecord.topicId);
                        if (integer == 0) {
                            topicRecordDAO.insert(topicRecord).subscribeOn(Schedulers.io()).subscribe();
                            Log.d(TAG, "onSuccess: 更新失败！执行插入操作");
                        }
                        Log.d(TAG, "onSuccess: 更新成功！");
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d(TAG, "onError: 原因" + e.getCause() + "错误信息：" + e.getMessage());
                    }
                });
    }

    public static class BriefViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserId, tvContent, tvLikeCount, tvCommentCount, tvCollectionCount;
        ImageButton ibLike, ibComment, ibCollection;

        public BriefViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvCollectionCount = itemView.findViewById(R.id.tvCollectionCount);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            ibCollection = itemView.findViewById(R.id.ibCollection);
        }
    }

    public static class BriefComparator extends DiffUtil.ItemCallback<Topic> {
        @Override
        public boolean areItemsTheSame(@NonNull Topic oldItem,
                                       @NonNull Topic newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Topic oldItem,
                                          @NonNull Topic newItem) {
            return newItem.id == oldItem.id;
        }
    }

}
