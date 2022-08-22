package com.scut.app.bbs.bean;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * 评论
 * @author 徐鑫
 */
public class Comment {
    public Long id;
    public String content;
    public int likeCount;
    public String time;
    public Long topicId;
    public String userId;

    public static class CommentDiffUtil extends DiffUtil.ItemCallback<Comment> {

        @Override
        public boolean areItemsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comment oldItem, @NonNull Comment newItem) {
            return oldItem.id.equals(newItem.id);
        }
    }
}
