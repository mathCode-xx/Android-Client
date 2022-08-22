package com.scut.app.bbs.adapter;

import android.os.Bundle;
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

import com.scut.app.MainActivity;
import com.scut.app.R;
import com.scut.app.bbs.bean.Topic;

/**
 * topic列表的适配器
 *
 * @author 徐鑫
 */
public class BriefAdapter extends PagingDataAdapter<Topic, BriefAdapter.BriefViewHolder> {

    private static final String TAG = "BriefAdapter";
    MainActivity activity;

    public BriefAdapter(MainActivity activity) {
        super(new TopicComparator());
        this.activity = activity;
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
            // TODO: 2022/8/12 记得要带参数过去
            Bundle bundle = new Bundle();
            bundle.putLong("topic", topic.id);
            Navigation.findNavController(v).navigate(R.id.action_bbsFragment_to_detailFragment, bundle);
            activity.hiddenBottom();
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

    public static class TopicComparator extends DiffUtil.ItemCallback<Topic> {
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
