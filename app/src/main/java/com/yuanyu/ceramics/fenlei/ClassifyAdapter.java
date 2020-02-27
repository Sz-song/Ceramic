package com.yuanyu.ceramics.fenlei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClassifyAdapter extends RecyclerView.Adapter<ClassifyAdapter.ViewHolder> {
    private List<ClassifyBean> list;
    private Context context;
    private OnPositionClickListener positionClickListener;

    public void setPositionClickListener(OnPositionClickListener positionClickListener) {
        this.positionClickListener = positionClickListener;
    }

    ClassifyAdapter(List<ClassifyBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classify, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.classify.setText(list.get(position).getClassify());
        if(list.get(position).isSelect()){
            holder.VerticalBar.setVisibility(View.VISIBLE);
            holder.classify.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white_drak));
        }else{
            holder.VerticalBar.setVisibility(View.GONE);
            holder.classify.setTextColor(context.getResources().getColor(R.color.blackLight));
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.transparent));
        }
        holder.itemView.setOnClickListener(v -> {
            for(int i=0;i<list.size();i++){
                list.get(i).setSelect(false);
            }
            list.get(position).setSelect(true);
            positionClickListener.callback(position);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.classify)
        TextView classify;
        @BindView(R.id.vertical_bar)
        View VerticalBar;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
