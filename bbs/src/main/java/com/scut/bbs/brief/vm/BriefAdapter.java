package com.scut.bbs.brief.vm;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.bbs.R;
import com.scut.bbs.brief.bean.TopicRecord;
import com.scut.bbs.detail.DetailActivity;
import com.scut.bbs.entity.Topic;
import com.scut.bbs.room.dao.TopicRecordDAO;
import com.scut.bbs.room.database.TopicRecordDatabase;

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

    private final AppCompatActivity context;

    public BriefAdapter(@NonNull BriefComparator briefComparator, AppCompatActivity context) {
        super(briefComparator);
        this.context = context;
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
        // TODO: 2022/8/8 不知道为什么滑动也会导致topicRecord变化，从而调用savaData()
        topic.getTopicRecord().observe(context, topicRecord -> {
            holder.ibLike.setImageResource(topicRecord.liked
                    ? R.drawable.ic_baseline_thumb_up_24_red
                    : R.drawable.ic_baseline_thumb_up_24_gray);
            holder.ibCollection.setImageResource(topicRecord.collected
                    ? R.drawable.ic_baseline_textsms_24_red
                    : R.drawable.ic_baseline_textsms_24_gray);
            saveData(topicRecord);
        });
        holder.ibLike.setOnClickListener(v -> {
            CharSequence text = holder.tvLikeCount.getText();
            String s = String.valueOf(text);
            int likeCount = Integer.parseInt(s);
            TopicRecord topicRecord = topic.getTopicRecord().getValue();
            if (topicRecord == null) {
                return;
            }
            if (topicRecord.liked) {
                likeCount--;
            } else {
                likeCount++;
            }
            // TODO: 2022/8/8 一直new对象，可能导致内存爆炸。先让程序跑起来，以后来优化
            TopicRecord tr = new TopicRecord(topicRecord.topicId, !topicRecord.liked, topicRecord.collected);
            topic.getTopicRecord().setValue(tr);
            holder.tvLikeCount.setText(String.valueOf(likeCount));
        });
        holder.ibCollection.setOnClickListener(v -> {
            CharSequence text = holder.tvCollectionCount.getText();
            String s = String.valueOf(text);
            int collectionCount = Integer.parseInt(s);
            TopicRecord topicRecord = topic.getTopicRecord().getValue();
            if (topicRecord == null) {
                return;
            }
            if (topicRecord.collected) {
                collectionCount--;
            } else {
                collectionCount++;
            }
            // TODO: 2022/8/8 一直new对象，可能导致内存爆炸。先让程序跑起来，以后来优化
            TopicRecord tr = new TopicRecord(topicRecord.topicId, topicRecord.liked, !topicRecord.collected);
            topic.getTopicRecord().setValue(tr);
            holder.tvCollectionCount.setText(String.valueOf(collectionCount));
        });
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            context.startActivity(intent);
        });
        holder.tvUserId.setText(topic.userId);
        holder.tvContent.setText(topic.content);
        holder.tvLikeCount.setText(String.valueOf(topic.likeCount));
        holder.tvCommentCount.setText(String.valueOf(topic.commentCount));
        holder.tvCollectionCount.setText(String.valueOf(topic.collectionCount));
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
            return oldItem.id == newItem.id;
        }
    }

}
