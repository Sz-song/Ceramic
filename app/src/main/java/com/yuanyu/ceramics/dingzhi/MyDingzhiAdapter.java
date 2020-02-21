package com.yuanyu.ceramics.dingzhi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyDingzhiAdapter extends RecyclerView.Adapter<MyDingzhiAdapter.ViewHolder> {
    private Context context;
    private List<MyDingzhiBean> list;
    private int type;//0用户端 1 商家

    MyDingzhiAdapter(Context context, List<MyDingzhiBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_dingzhi, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GlideApp.with(context)
                .load(AppConstant.BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.img_default)
                .override(50, 50)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName());
        if(list.get(position).getPrice().equals("0")){
            holder.price.setText("预算:1000-5000");
        }else if(list.get(position).getPrice().equals("1")){
            holder.price.setText("预算:5001-50000");
        }else if(list.get(position).getPrice().equals("2")){
            holder.price.setText("预算:50000以上");
        }else{
            holder.price.setText("预算:1000-5000");
        }
        holder.detail.setText(list.get(position).getDetail());
        if(list.get(position).getUseage().length()>0){
            holder.useage.setVisibility(View.VISIBLE);
            holder.useage.setText(list.get(position).getUseage());
        }else{
            holder.useage.setVisibility(View.GONE);
        }
        //0 正在审核，1 平台审核成功 2，平台审核失败，3，大师拒绝接单，4，大师接单未缴纳保证金，5商家接单缴纳了保证金，6,支付尾款未发货，7商家发货，8买家收货
        switch (list.get(position).getStatus()) {
            case 0://正在审核
                holder.status.setText("定制正在审核");
                break;
            case 1://1 平台审核成功
                holder.status.setText("等待大师接单");
                break;
            case 2://平台审核失败
                holder.status.setText("审核失败");
                break;
            case 3://大师拒绝接单
                holder.status.setText("大师拒绝");
                break;
            case 4://大师接单未缴纳保证金
                holder.status.setText("等待缴纳保证金");
                break;
            case 5://大师接单缴纳了保证金
                holder.status.setText("正在制作");
                break;
            case 6:
                holder.status.setText("等待发货");
                break;
            case 7://商家发货
                holder.status.setText("商家已发货");
                break;
            case 8://买家收货
                holder.status.setText("定制成功");
                break;
        }
        holder.itemView.setOnClickListener(v -> {
            if(type==0){
                Intent intent=new Intent(context,DingzhiDetailUserActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }else if(type==1){
                Intent intent=new Intent(context,ShopDingzhiDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }

        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.useage)
        TextView useage;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
