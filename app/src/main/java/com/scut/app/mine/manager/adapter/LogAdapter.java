package com.scut.app.mine.manager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.app.R;
import com.scut.app.entity.OptLogDTO;

public class LogAdapter extends ListAdapter<OptLogDTO, LogAdapter.LogViewHolder> {

    public LogAdapter() {
        super(new OptLogDTO.LogDiff());
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        OptLogDTO item = getItem(position);

        holder.tvOperator.setText(item.operatorId);
        holder.tvOptDes.setText(item.description);
        holder.tvOptObj.setText(item.params);
        holder.tvOptTime.setText(item.optTime);
        holder.tvOptResult.setText(item.optType);
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tvOperator, tvOptDes, tvOptTime, tvOptResult, tvOptObj;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOperator = itemView.findViewById(R.id.tvOperator);
            tvOptDes = itemView.findViewById(R.id.tvOptDes);
            tvOptTime = itemView.findViewById(R.id.tvOptTime);
            tvOptResult = itemView.findViewById(R.id.tvOptResult);
            tvOptObj = itemView.findViewById(R.id.tvOptObj);
        }
    }
}
