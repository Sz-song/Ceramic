package com.yuanyu.ceramics.seller.refund;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.refund.refund_detail.RefundDetailActivity;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cat on 2018/8/24.
 */

public class RefundAdapter extends RecyclerView.Adapter<RefundAdapter.ViewHolder> {
    private List<RefundBean> list;
    private Context context;
    public RefundAdapter(List<RefundBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_refund_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getSale_portrait())
                .override(100,100)
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        holder.name.setText(list.get(position).getSale_name());
        holder.price.setText(list.get(position).getRefund_money());
        holder.num.setText("x"+list.get(position).getRefund_num());
        holder.refundReason.setText(list.get(position).getRefund_type());
        holder.time.setText(Md5Utils.CountTime(list.get(position).getTime()));
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context, RefundDetailActivity.class);
            intent.putExtra("ordernum",list.get(position).getOrdernum());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price_txt)
        TextView priceTxt;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.time_txt)
        TextView timeTxt;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.refund_reason)
        TextView refundReason;
        @BindView(R.id.bottom_relat)
        RelativeLayout bottomRelat;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
