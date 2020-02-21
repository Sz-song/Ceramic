package com.yuanyu.ceramics.common.view.mypicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourierTimePickerAdapter extends RecyclerView.Adapter<CourierTimePickerAdapter.ViewHolder> {
    private Context context;
    private List<CourierTimeBean> list;
    private OnPositionClickListener listener;

    public void setOnPositionClickListener(OnPositionClickListener listener) {
        this.listener = listener;
    }

    public CourierTimePickerAdapter(Context context, List<CourierTimeBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_courier_time_picker, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.time.setText(list.get(position).getTime());
        if(list.get(position).isIspass()){
            holder.time.setTextColor(context.getResources().getColor(R.color.lightGray));
        }else {
            holder.time.setTextColor(context.getResources().getColor(R.color.blackLight));
        }
        holder.itemView.setOnClickListener(view -> {
            if(list.get(position).isIspass()){
                Toast.makeText(context, "时间已过", Toast.LENGTH_SHORT).show();
            }else {
                listener.callback(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.time)
        TextView time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
