package com.yuanyu.ceramics.logistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogisticsAdapter extends RecyclerView.Adapter<LogisticsAdapter.ViewHolder> {
    private Context mContext;
    private List<LogisticsBean.Logistics> list;

    public LogisticsAdapter(Context context, List<LogisticsBean.Logistics> list) {
        this.mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_logistics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.timeDay.setText(list.get(position).getAcceptTime().substring(5,10));
        holder.time.setText(list.get(position).getAcceptTime().substring(11,16));
        holder.context.setText(list.get(position).getAcceptStation());
        if(list.size()==1){
            holder.lineBottom.setVisibility(View.GONE);
            holder.lineTop.setVisibility(View.GONE);
            holder.centerImg.setVisibility(View.GONE);
        }else if(list.size()>1){
            if(position==0){
                holder.lineBottom.setVisibility(View.VISIBLE);
                holder.lineTop.setVisibility(View.GONE);
                holder.centerImg.setVisibility(View.VISIBLE);
            }else if(position==list.size()-1){
                holder.lineBottom.setVisibility(View.GONE);
                holder.lineTop.setVisibility(View.VISIBLE);
                holder.centerImg.setVisibility(View.GONE);
            }else{
                holder.lineBottom.setVisibility(View.VISIBLE);
                holder.lineTop.setVisibility(View.VISIBLE);
                holder.centerImg.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.time_day)
        TextView timeDay;
        @BindView(R.id.line_top)
        View lineTop;
        @BindView(R.id.line_bottom)
        View lineBottom;
        @BindView(R.id.center_view)
        View centerView;
        @BindView(R.id.center_img)
        ImageView centerImg;
        @BindView(R.id.context)
        TextView context;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
