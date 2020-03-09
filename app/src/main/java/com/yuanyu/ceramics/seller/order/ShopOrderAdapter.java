package com.yuanyu.ceramics.seller.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.order.detail.ShopOrderDetailActivity;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ShopOrderAdapter extends RecyclerView.Adapter<ShopOrderAdapter.ViewHolder> {
    private Context context;
    private List<ShopOrderBean> list;

    public ShopOrderAdapter(Context context, List<ShopOrderBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_seller_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getImage())
                .override(200, 200)
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("¥" + list.get(position).getPrice());
        holder.num.setText("×" + list.get(position).getNum());
        holder.time.setText(TimeUtils.CountTime(list.get(position).getTime()));
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ShopOrderDetailActivity.class);
            intent.putExtra("ordernum",list.get(position).getOrdernum());
            context.startActivity(intent);
        });
        if (list.get(position).getType() == 1) {//待付款订单
            holder.content.setVisibility(View.GONE);
            holder.bottomRelat.setVisibility(View.GONE);
        } else if (list.get(position).getType() == 2) {//待发货订单
            holder.content.setVisibility(View.GONE);
            holder.bottomRelat.setVisibility(View.VISIBLE);
            holder.bottomTxt1.setVisibility(View.GONE);
            holder.bottomTxt2.setVisibility(View.GONE);
            holder.bottomTxt3.setVisibility(View.GONE);
            holder.orderBtn1.setVisibility(View.VISIBLE);
            holder.orderBtn2.setVisibility(View.GONE);
            holder.orderBtn1.setText("发货");
            holder.orderBtn1.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DeliveryActivity.class);
//                intent.putExtra("ordernum",list.get(position).getOrdernum());
//                context.startActivity(intent);
            });
        } else if (list.get(position).getType() == 3) {//已发货订单
            holder.content.setVisibility(View.GONE);
            holder.bottomRelat.setVisibility(View.VISIBLE);
            holder.bottomTxt1.setVisibility(View.GONE);
            holder.bottomTxt2.setVisibility(View.GONE);
            holder.bottomTxt3.setVisibility(View.GONE);
            holder.orderBtn1.setVisibility(View.VISIBLE);
            holder.orderBtn1.setText("查看物流");
            holder.orderBtn1.setOnClickListener(view -> {
//                Intent intent=new Intent(context,LogisticsTracingActivity.class);
//                intent.putExtra("image",list.get(position).getImage());
//                intent.putExtra("logistics",list.get(position).getLogisticsnum());
//                intent.putExtra("logistics_id",list.get(position).getLogisticscompany());
//                context.startActivity(intent);
            });
            holder.orderBtn2.setVisibility(View.VISIBLE);
            holder.orderBtn2.setText("重新发货");
            holder.orderBtn2.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DeliveryActivity.class);
//                intent.putExtra("ordernum",list.get(position).getOrdernum());
//                context.startActivity(intent);
            });
        } else if (list.get(position).getType() == 4) {//待评价（已收货）
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("交易成功");
            holder.bottomRelat.setVisibility(View.GONE);
        }  else if (list.get(position).getType() == 5) {//待评价（已收货）
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("退款订单");
            holder.bottomRelat.setVisibility(View.GONE);
        } else if (list.get(position).getType() == 6) {//订单取消
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("订单取消");
            holder.bottomRelat.setVisibility(View.GONE);
        } else if (list.get(position).getType() == 7) {//异常订单
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("订单异常");
            holder.bottomRelat.setVisibility(View.GONE);
        } else if (list.get(position).getType() == 8) {//已评价订单（已收货）
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("交易成功");
            holder.bottomRelat.setVisibility(View.GONE);
        } else if (list.get(position).getType() == 9) {//已结算订单
            holder.content.setVisibility(View.GONE);
            holder.bottomRelat.setVisibility(View.VISIBLE);
            holder.bottomTxt1.setVisibility(View.VISIBLE);
            holder.bottomTxt2.setVisibility(View.VISIBLE);
            holder.bottomTxt3.setVisibility(View.VISIBLE);
            holder.orderBtn1.setVisibility(View.GONE);
            holder.orderBtn2.setVisibility(View.GONE);
        }
        if(list.get(position).getRefund_status()!=4&&list.get(position).getRefund_status()!=2) {
            holder.content.setVisibility(View.VISIBLE);
            holder.content.setText("退款售后订单");
            holder.bottomRelat.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(view -> {
//                Intent intent=new Intent(context,RefundDetailActivity.class);
//                intent.putExtra("ordernum",list.get(position).getOrdernum());
//                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.price_txt)
        TextView priceTxt;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.time_txt)
        TextView timeTxt;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.order_btn1)
        TextView orderBtn1;
        @BindView(R.id.order_btn2)
        TextView orderBtn2;
        @BindView(R.id.bottom_txt1)
        TextView bottomTxt1;
        @BindView(R.id.bottom_txt2)
        TextView bottomTxt2;
        @BindView(R.id.bottom_txt3)
        TextView bottomTxt3;
        @BindView(R.id.bottom_relat)
        RelativeLayout bottomRelat;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
