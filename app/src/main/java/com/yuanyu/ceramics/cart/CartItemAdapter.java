package com.yuanyu.ceramics.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.ViewHolder> {
    private Context context;
    private List<CartBean.CartItemBean> list;
    private OnPositionClickListener deleteListener;
    private OnPositionClickListener checkListener;

    void setDeleteListener(OnPositionClickListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    void setCheckListener(OnPositionClickListener checkListener) {
        this.checkListener = checkListener;
    }

    CartItemAdapter(Context context, List<CartBean.CartItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CartItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartItemAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemAdapter.ViewHolder holder, int position) {
        holder.itemCheckbox.setChecked(list.get(position).isSelect());
        Glide.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .override(100,100)
                .into(holder.itemImage);
        holder.itemName.setText(list.get(position).getCommodityname());
        holder.price.setText("¥"+list.get(position).getPrice());
        holder.delete.setOnClickListener(v -> deleteListener.callback(position));
        holder.itemCheckbox.setOnClickListener(v -> checkListener.callback(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_checkbox)
        SmoothCheckBox itemCheckbox;
        @BindView(R.id.item_image)
        RoundedImageView itemImage;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.delete)
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
