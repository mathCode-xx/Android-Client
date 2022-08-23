package com.scut.app.mine.manager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.scut.app.R;
import com.scut.app.entity.User;
import com.scut.app.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AuditAdapter extends RecyclerView.Adapter<AuditAdapter.AuditViewHolder> {
    private static final String TAG = "AuditAdapter";

    /**
     * 审核模式
     */
    private boolean auditModel = false;
    /**
     * 用来记录选择的复选框
     */
    private List<Boolean> selectedUser;
    private List<User> userList;
    private final List<String> commitUserId = new ArrayList<>();
    private final LongClickListener listener;

    public AuditAdapter(LongClickListener listener) {
        this.listener = listener;
        userList = Collections.emptyList();
    }

    @NonNull
    @Override
    public AuditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_audit, parent, false);
        return new AuditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuditViewHolder holder, int position) {
        User user = userList.get(position);
        if (auditModel) {
            final int index = position;
            //显示复选框
            holder.cbAudit.setVisibility(View.VISIBLE);
            //当处于审核模式时，点击item可以对复选框进行操作
            holder.itemView.setOnClickListener(v -> holder.cbAudit.callOnClick());

            holder.cbAudit.setChecked(selectedUser.get(index));
            holder.cbAudit.setOnClickListener(v -> selectedUser.set(index, holder.cbAudit.isChecked()));
        } else {
            holder.cbAudit.setVisibility(View.GONE);
        }
        holder.tvName.setText(user.name);
        holder.tvStuId.setText(user.id);
        holder.tvMajor.setText(user.major);
        holder.itemView.setOnLongClickListener(v -> {
            auditModel = true;
            this.listener.listener();
            notifyDataSetChanged();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void deleteItem() {
        if (userList == null || selectedUser == null) {
            return;
        }
        for (int i = 0; i < selectedUser.size(); i++) {
            if (selectedUser.get(i)) {
                Log.d(TAG, "deleteItem: " + i);
                userList.remove(i);
                selectedUser.remove(i);
                notifyItemRemoved(i);
                i--;
            }
        }
        ToastUtils.show("操作完成");
        auditModel = false;
        notifyDataSetChanged();
    }

    public void selectAll() {
        for (int i = 0; i < selectedUser.size(); i++) {
            selectedUser.set(i, true);
        }
        notifyDataSetChanged();
    }

    public void cancelSelect() {
        for (int i = 0; i < selectedUser.size(); i++) {
            selectedUser.set(i, false);
        }
        notifyDataSetChanged();
    }

    public void setUserList(List<User> userList) {
        Log.d(TAG, "setUserList: ");
        this.userList = userList;
        if (this.selectedUser == null) {
            this.selectedUser = new ArrayList<>(userList.size());
        }
        for (int i = 0; i < userList.size(); i++) {
            this.selectedUser.add(false);
        }
        auditModel = false;
    }

    public List<String> getCommitUserId() {
        for (int i = 0; i < getItemCount(); i++) {
            if (selectedUser.get(i)) {
                Log.d(TAG, "getCommitUserId: " + userList.get(i).id);
                commitUserId.add(userList.get(i).id);
            }
        }
        return commitUserId;
    }

    public interface LongClickListener {
        void listener();
    }

    static class AuditViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvStuId, tvMajor;
        CheckBox cbAudit;

        public AuditViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvStuId = itemView.findViewById(R.id.tvStuId);
            tvMajor = itemView.findViewById(R.id.tvMajor);
            cbAudit = itemView.findViewById(R.id.cbAudit);
        }
    }
}
