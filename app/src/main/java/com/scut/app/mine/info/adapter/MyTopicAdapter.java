package com.scut.app.mine.info.adapter;

import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.app.R;
import com.scut.app.bbs.bean.Topic;
import com.scut.app.entity.ResponseData;
import com.scut.app.mine.info.vm.MyBbsViewModel;
import com.scut.app.net.CallBack;
import com.scut.app.util.ToastUtils;

import java.util.List;

/**
 * 我的主贴适配器
 *
 * @author 徐鑫
 */
public class MyTopicAdapter extends RecyclerView.Adapter<MyTopicAdapter.MyTopicViewHolder> {
    private List<Topic> topicList;
    private boolean deleteModel = false;
    private final MyBbsViewModel mViewModel;
    ProgressDialog dialog;

    public MyTopicAdapter(MyBbsViewModel viewModel) {
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public MyTopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_topic, parent, false);
        dialog = new ProgressDialog(parent.getContext());
        dialog.setMessage("正在删除");
        return new MyTopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTopicViewHolder holder, int position) {
        Topic topic = topicList.get(position);
        if (topic == null) {
            return;
        }
        if (deleteModel) {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnDelete.setOnClickListener(v -> {
                dialog.show();
                mViewModel.deleteTopic(topic, new CallBack() {
                    @Override
                    public void success(ResponseData responseData) {
                        topicList.remove(topic);
                        notifyDataSetChanged();
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        ToastUtils.show(responseData.getMessage());
                    }

                    @Override
                    public void fail(String message) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        ToastUtils.show(message);
                    }
                });
            });
            holder.itemView.setOnClickListener(v -> {
                deleteModel = false;
                notifyDataSetChanged();
            });
        } else {
            holder.itemView.setOnLongClickListener(v -> {
                deleteModel = true;
                notifyDataSetChanged();
                return true;
            });
            holder.btnDelete.setVisibility(View.GONE);
        }

        holder.ibLike.setOnClickListener(v -> {

        });
        holder.ibCollection.setOnClickListener(v -> {

        });
        holder.tvUserId.setText(topic.userId);
        holder.tvContent.setText(topic.content);
        holder.tvLikeCount.setText(String.valueOf(topic.likeCount));
        holder.tvCommentCount.setText(String.valueOf(topic.commentCount));
        holder.tvCollectionCount.setText(String.valueOf(topic.collectionCount));



    }

    @Override
    public int getItemCount() {
        return topicList == null ? 0 : topicList.size();
    }


    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    static class MyTopicViewHolder extends RecyclerView.ViewHolder {

        TextView tvUserId, tvContent, tvLikeCount, tvCommentCount, tvCollectionCount;
        ImageButton ibLike, ibComment, ibCollection;
        Button btnDelete;

        public MyTopicViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tvUserId);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvLikeCount = itemView.findViewById(R.id.tvLikeCount);
            tvCommentCount = itemView.findViewById(R.id.tvCommentCount);
            tvCollectionCount = itemView.findViewById(R.id.tvCollectionCount);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            ibCollection = itemView.findViewById(R.id.ibCollection);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

}


