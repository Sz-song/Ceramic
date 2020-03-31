package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveFilterAdapter extends RecyclerView.Adapter<LiveFilterAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private int position;
    private OnPositionClickListener clickListener;

    public void setClickListener(OnPositionClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public LiveFilterAdapter(Context context, List<String> list, int position) {
        this.context = context;
        this.list = list;
        this.position = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_live_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int pos) {
        holder.name.setText(list.get(pos));
        if(position==pos){
            holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.name.setTextColor(context.getResources().getColor(R.color.blackLight));
        }
        holder.itemView.setOnClickListener(view -> {
            position=pos;
            notifyDataSetChanged();
            clickListener.callback(pos);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView name;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
