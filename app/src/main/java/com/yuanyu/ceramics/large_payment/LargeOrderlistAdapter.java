package com.yuanyu.ceramics.large_payment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LargeOrderlistAdapter extends RecyclerView.Adapter<LargeOrderlistAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public LargeOrderlistAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_largeorderlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderNum.setText(list.get(position));
        holder.copyOrdernum.setOnClickListener(v -> copy(holder.orderNum.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    private void copy(String str) {
        try {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", str);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "复制失败", Toast.LENGTH_SHORT).show();
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.order_num)
        TextView orderNum;
        @BindView(R.id.copy_ordernum)
        TextView copyOrdernum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
