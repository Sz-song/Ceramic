package com.yuanyu.ceramics.mycoins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCoinsDetailAdapter extends RecyclerView.Adapter<MyCoinsDetailAdapter.ViewHolder> {
    private Context context;
    private List<MyCoinsDetailBean> list;

    MyCoinsDetailAdapter(Context context, List<MyCoinsDetailBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_coins_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.source.setText(list.get(position).getSource());
        holder.time.setText(Md5Utils.CountTime(list.get(position).getTime()));
        if(list.get(position).getNum()>=0){
            holder.num.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.num.setText("+"+list.get(position).getNum());
        }else{
            holder.num.setTextColor(context.getResources().getColor(R.color.gold));
            holder.num.setText(list.get(position).getNum()+"");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.num)
        TextView num;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
