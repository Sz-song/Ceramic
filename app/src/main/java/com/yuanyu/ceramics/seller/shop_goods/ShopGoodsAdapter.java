package com.yuanyu.ceramics.seller.shop_goods;

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
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.shop_shelve.re_shelve.ReShelveActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShopGoodsAdapter extends RecyclerView.Adapter<ShopGoodsAdapter.ViewHolder> {
    private List<ShopGoodsBean> list;
    private Context context;
    private OnPositionClickListener onDeleteClickListener;
    private OnPositionClickListener onOffShelvesClickListener;

    public void setOnDeleteClickListener(OnPositionClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setOnOffShelvesClickListener(OnPositionClickListener onOffShelvesClickListener) {
        this.onOffShelvesClickListener = onOffShelvesClickListener;
    }

    public ShopGoodsAdapter(List<ShopGoodsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_shop_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getPortrait())
                .override(100,100)
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice());
        if(list.get(position).getArtist()!=null&&list.get(position).getArtist().trim().length()>0){
            holder.artist.setText(list.get(position).getArtist());
        }else{
            holder.artist.setText("无");
        }
        holder.num.setText("x"+list.get(position).getNum());
        if(list.get(position).getType()==1){
            holder.bottomRelat.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.GONE);
            holder.goodsBtn1.setText("下架");
            holder.goodsBtn1.setOnClickListener(view -> {
                onOffShelvesClickListener.callback(position);
            });
        }else if(list.get(position).getType()==2){
            holder.bottomRelat.setVisibility(View.VISIBLE);
            holder.delete.setVisibility(View.VISIBLE);
            holder.goodsBtn1.setText("重新上架");
            holder.delete.setOnClickListener(view -> {
                onDeleteClickListener.callback(position);
            });
            holder.goodsBtn1.setOnClickListener(view -> {
                Intent intent=new Intent(context, ReShelveActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            });
        }else if(list.get(position).getType()==3){
            holder.bottomRelat.setVisibility(View.GONE);
        }
            holder.itemView.setOnClickListener(view -> {
            Intent intent=new Intent(context,ViewShopGoodsActivity.class);
            intent.putExtra("id",list.get(position).getId());
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
        @BindView(R.id.artist_txt)
        TextView artistTxt;
        @BindView(R.id.artist)
        TextView artist;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.goods_btn1)
        TextView goodsBtn1;
        @BindView(R.id.delete)
        TextView delete;
        @BindView(R.id.bottom_relat)
        RelativeLayout bottomRelat;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
