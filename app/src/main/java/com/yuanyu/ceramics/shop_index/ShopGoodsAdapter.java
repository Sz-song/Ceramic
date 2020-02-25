package com.yuanyu.ceramics.shop_index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopGoodsAdapter extends RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder> {
    private Context context;
    private List<ShopGoodsBean> list;

    ShopGoodsAdapter(Context context, List<ShopGoodsBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_shopindex_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .override(200,200)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("¥"+list.get(position).getPrice());
//        holder.itemView.setOnClickListener(view -> ItemDetailActivity.actionStart(context, list.get(position).getId()));
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

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
