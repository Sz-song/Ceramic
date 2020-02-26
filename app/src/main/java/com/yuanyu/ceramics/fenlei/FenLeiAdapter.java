package com.yuanyu.ceramics.fenlei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FenLeiAdapter  extends RecyclerView.Adapter<FenLeiAdapter.ViewHolder>  {

    private Context context;
    private List<FenLeiBean> list;
    private OnNoDataListener onCellClickListener;

    public void setOnCellClickListener(OnNoDataListener onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    FenLeiAdapter(Context context, List<FenLeiBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_fenlei, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        if(list.get(position).isSelect()){
            holder.itemView.setBackgroundResource(R.drawable.circle_selected_bg);
        }else{
            holder.itemView.setBackgroundResource(R.drawable.circle_bg);
        }
        holder.itemView.setOnClickListener(v -> {
            if(list.get(position).isSelect()){
                list.get(position).setSelect(!list.get(position).isSelect());
            }else{
                int count =0;
                for(int i=0;i<list.size();i++){
                    if(list.get(i).isSelect()){count++;}
                }
                if(count>=3){
                    Toast.makeText(context, "每项选择不超过3个", Toast.LENGTH_SHORT).show();
                }else{
                    list.get(position).setSelect(!list.get(position).isSelect());
                }
            }
            notifyDataSetChanged();
            onCellClickListener.setNodata();
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
