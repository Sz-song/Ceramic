package com.yuanyu.ceramics.broadcast;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.broadcast.pull.LivePullActivity;
import com.yuanyu.ceramics.broadcast.push.LivePushActivity;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder> {
    private Context context;
    private List<BroadcastBean> list;
    private OnPositionClickListener onSuscribeClick;
    BroadcastAdapter(Context context, List<BroadcastBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnSuscribeClick(OnPositionClickListener onSuscribeClick) {
        this.onSuscribeClick = onSuscribeClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_zhibogridview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.watchNum.setText("观看人数"+list.get(position).getWatch());
        holder.shopName.setText(list.get(position).getShop());
        holder.name.setText(list.get(position).getName());
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        if(list.get(position).isIssubscribe()){
            holder.suscribe.setText("已订阅");
        }else{
            holder.suscribe.setText("+订阅");
        }
        holder.suscribe.setOnClickListener(view -> {
            if(list.get(position).isIssubscribe()){
                Toast.makeText(context, "您已经订阅过了", Toast.LENGTH_SHORT).show();
            }else{
                onSuscribeClick.callback(position);
            }
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, LivePullActivity.class);
            intent.putExtra("live_id",list.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        RoundedImageView image;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.suscribe)
        TextView suscribe;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.watch_num)
        TextView watchNum;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
