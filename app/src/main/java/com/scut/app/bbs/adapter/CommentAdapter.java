package com.scut.app.bbs.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.scut.app.R;
import com.scut.app.bbs.bean.Comment;

/**
 * 帖子详情页的评论适配器
 *
 * @author 徐鑫
 */
public class CommentAdapter extends ListAdapter<Comment, CommentAdapter.DetailViewHolder> {

    public CommentAdapter() {
        super(new Comment.CommentDiffUtil());
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_comment, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Comment comment = getItem(position);
        if (comment == null) {
            return;
        }
        holder.tvUserId.setText(comment.userId);
        holder.tvComment.setText(comment.content);
    }

    public static class DetailViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView ivUserPhoto;
        TextView tvUserId, tvComment;

        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.ivCommentPhoto);
            tvUserId = itemView.findViewById(R.id.tvCommentUser);
            tvComment = itemView.findViewById(R.id.tvComment);
        }
    }

    public void addComment(Comment comment) {
        getCurrentList().add(comment);
    }
}
