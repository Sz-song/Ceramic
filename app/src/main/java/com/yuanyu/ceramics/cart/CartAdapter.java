package com.yuanyu.ceramics.cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.OnStringCallback;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartBean> list;
    private OnStringCallback onDeleteListener;
    private OnNoDataListener onReflashListener;
    CartAdapter(Context context, List<CartBean> list) {
        this.context = context;
        this.list = list;
    }

    void setOnDeleteListener(OnStringCallback onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    void setOnReflashListener(OnNoDataListener onReflashListener) {
        this.onReflashListener = onReflashListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.shopName.setText(list.get(position).getShopname());
        Glide.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getShoplogo())
                .placeholder(R.drawable.img_default)
                .override(50,50)
                .into(holder.shopPortrait);
        holder.shopName.setOnClickListener(v -> {
//            Intent intent=new Intent(context, ShopIndexActivity.class);
//            intent.putExtra("shopid",list.get(position).getShopid());
//            context.startActivity(intent);
        });
        boolean shopChecked =true;
        for(int i = 0;i < list.get(position).getList().size();i++){
            if(!list.get(position).getList().get(i).isSelect()){
                shopChecked=false;
            }
        }
        holder.shopCheckBox.setChecked(shopChecked);
        holder.shopPortrait.setOnClickListener(v -> {
//            Intent intent=new Intent(context, ShopIndexActivity.class);
//            intent.putExtra("shopid",list.get(position).getShopid());
//            context.startActivity(intent);
        });
        CartItemAdapter adapter=new CartItemAdapter(context,list.get(position).getList());
        adapter.setDeleteListener(position1 -> {
            DeleteDialog dialog=new DeleteDialog(context);
            dialog.setNoStr("删除");
            dialog.setYesStr("取消");
            dialog.setTitle("确定要在购物车内删除该商品吗？");
            dialog.setYesOnclickListener(dialog::dismiss);
            dialog.setNoOnclickListener(() -> {
                onDeleteListener.callback(list.get(position).getList().get(position1).getId());
                dialog.dismiss();
            });
            dialog.show();
        });
        adapter.setCheckListener(position1 -> {
            list.get(position).getList().get(position1).setSelect(!list.get(position).getList().get(position1).isSelect());
            notifyDataSetChanged();
        });
        holder.recyclerview.setLayoutManager(new LinearLayoutManager(context){
            @Override
            public boolean canScrollVertically() {return false;}
            @Override
            public boolean canScrollHorizontally() {return false;}
        });
        holder.recyclerview.setAdapter(adapter);
        holder.shopCheckBox.setOnClickListener(view -> {
            for(int i = 0;i < list.get(position).getList().size();i++){
                list.get(position).getList().get(i).setSelect(!holder.shopCheckBox.isChecked());
            }
            notifyDataSetChanged();
        });
        onReflashListener.setNodata();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.shop_checkBox)
        SmoothCheckBox shopCheckBox;
        @BindView(R.id.shop_portrait)
        RoundedImageView shopPortrait;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}