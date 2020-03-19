package com.yuanyu.ceramics.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


/**
 * Created by cat on 2018/7/26.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private List<Shop> shopList;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.touxiang)
        ImageView touxiang;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.jindian_btn)
        TextView jindianBtn;
        @BindView(R.id.yishou_num)
        TextView yishouNum;
        @BindView(R.id.haopin_num)
        TextView haopinNum;
        @BindView(R.id.fensi_num)
        TextView fensiNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ShopAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from( parent.getContext()).inflate(R.layout.cell_searchresult_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Shop shop = shopList.get(position);
        GlideApp.with(context)
                .load(BASE_URL+shop.getPortrait())
                .placeholder(R.drawable.img_default)
                .into(holder.touxiang);
        holder.name.setText(shop.getName());
        holder.yishouNum.setText(shop.getYishou());
        holder.haopinNum.setText(shop.getHaopin());
        holder.fensiNum.setText(shop.getFensi());
        holder.jindianBtn.setOnClickListener(view -> {
            Intent intent  = new Intent(context, ShopIndexActivity.class);
            intent.putExtra(AppConstant.SHOP_ID,shop.getShopid());
            context.startActivity(intent);
        });
        holder.itemView.setOnClickListener(view -> {
            Intent intent  = new Intent(context, ShopIndexActivity.class);
            intent.putExtra(AppConstant.SHOP_ID,shop.getShopid());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }


}
