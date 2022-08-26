package com.scut.app.entity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class OptLogDTO {

    public Long id;

    public String operatorId;

    /**
     * 操作描述
     */
    public String description;

    public String optType;

    public String params;

    public String optTime;

    public static class LogDiff extends DiffUtil.ItemCallback<OptLogDTO> {

        @Override
        public boolean areItemsTheSame(@NonNull OptLogDTO oldItem, @NonNull OptLogDTO newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull OptLogDTO oldItem, @NonNull OptLogDTO newItem) {
            return oldItem.id.equals(newItem.id);
        }
    }
}
