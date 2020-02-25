package com.yuanyu.ceramics.shop_index;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.FenleiBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridLayoutAdapter extends RecyclerView.Adapter<GridLayoutAdapter.ViewHolder> {
    private Context context;
    private List<FenleiBean> list=new ArrayList<>();
    private boolean isExpand;
    private OnItemClickListener onItemClickListener;
    public GridLayoutAdapter(Context context) {
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.cell_gridlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.text.setText(list.get(position).getName());
        if(list.get(position).isCheck()){
            holder.text.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.text.setTextColor(context.getResources().getColor(R.color.blackLight));
        }
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(position));
    }

    @Override
    public int getItemCount() {
        if(list.size()<3){
            return list.size();
        }else {
            if(isExpand){
                return list.size();
            }else {
                return 3;
            }
        }
    }

    public void notifyDataSetChanged(boolean isExpand, final List<FenleiBean> tempData) {
        if (tempData == null || 0 == tempData.size()) {
            return;
        }
        list.clear();
        // 如果是展开的，则加入全部data，反之则只显示3条
        if (isExpand) {
            list.addAll(tempData);
        } else {
            if(tempData.size()>2) {
                list.add(tempData.get(0));
                list.add(tempData.get(1));
                list.add(tempData.get(2));
            }else{
                list.addAll(tempData);
            }
        }
        notifyDataSetChanged();
    }
    public void setExpand(){
        isExpand=!isExpand;
    }
    public boolean getExpand(){
        return isExpand;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public interface OnItemClickListener {
        void onClick(int position);
    }
}
