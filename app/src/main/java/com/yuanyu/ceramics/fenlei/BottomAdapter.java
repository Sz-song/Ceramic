package com.yuanyu.ceramics.fenlei;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.ViewHolder> {
    private List<String> list;
    private OnPositionClickListener onClearClickListener;
    BottomAdapter(List<String> list) {this.list = list;}

    public void setOnClearClickListener(OnPositionClickListener onClearClickListener) {
        this.onClearClickListener = onClearClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fenlei_recy_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        if (list.get(position).equals("")) {
            holder.button.setVisibility(View.GONE);
        } else {
            holder.button.setVisibility(View.VISIBLE);
        }
        holder.button.setText(list.get(position));
        holder.button.setActivated(true);
        holder.button.setOnClickListener(view -> {
            list.set(position, "");
            notifyDataSetChanged();
            onClearClickListener.callback(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recybtn)
        Button button;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


