package com.yuanyu.ceramics.seller.delivery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class WaitDeliveryAdapter extends RecyclerView.Adapter<WaitDeliveryAdapter.ViewHolder> {
    private List<WaitDeliveryBean> list;
    private Context context;

    WaitDeliveryAdapter(List<WaitDeliveryBean> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_waitdelivery, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL+list.get(position).getPortrait())
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("¥"+list.get(position).getPrice());
        holder.num.setText("x"+list.get(position).getNum());
        holder.time.setText(TimeUtils.CountTime(list.get(position).getTime()));
        holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context,DeliveryActivity.class);
            intent.putExtra("ordernum",list.get(position).getOrdernum());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        SquareImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.time)
        TextView time;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
