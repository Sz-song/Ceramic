package com.yuanyu.ceramics.order.refund;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class RefundListAdapter extends RecyclerView.Adapter<RefundListAdapter.ViewHolder> {
    private List<RefundListBean> list;
    private Context context;

    public RefundListAdapter(List<RefundListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_refund_wujia, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getShop_portrait())
                .placeholder(R.drawable.img_default)
                .override(50,50)
                .into(holder.shopPortrait);
                holder.shopName.setText(list.get(position).getShop_name());
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.img_default)
                .into(holder.itemImage);
        holder.itemName.setText(list.get(position).getName());
        holder.itemNum.setText("x" + list.get(position).getNum());
        switch (list.get(position).getStatus()) {
            case "1": holder.status.setText("退款中");
                break;
            case "2": holder.status.setText("退款失败");
                break;
            case "3": holder.status.setText("退款成功");
                break;
            case "4": holder.status.setText("退款取消");
                break;
            case "5": holder.status.setText("退货退款申请中");
                break;
        }
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RefundDetailWujiaActivity.class);
            intent.putExtra("refund_num", list.get(position).getOrdernum());
            context.startActivity(intent);
        });
        holder.detail.setOnClickListener(view -> {
            Intent intent = new Intent(context, RefundDetailWujiaActivity.class);
            intent.putExtra("refund_num", list.get(position).getOrdernum());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_portrait)
        RoundedImageView shopPortrait;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_num)
        TextView itemNum;
        @BindView(R.id.item)
        RelativeLayout item;
        @BindView(R.id.status)
        TextView status;
        @BindView(R.id.detail)
        TextView detail;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
