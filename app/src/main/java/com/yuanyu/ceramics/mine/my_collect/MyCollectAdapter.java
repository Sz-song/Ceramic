package com.yuanyu.ceramics.mine.my_collect;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MyCollectAdapter extends RecyclerView.Adapter<MyCollectAdapter.ViewHolder> {
    private Context context;
    private List<MyCollectBean> list;
    private int width, margin;

    MyCollectAdapter(Context context, List<MyCollectBean> list, int width, int margin) {
        this.context = context;
        this.list = list;
        this.width = width;
        this.margin = margin;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homepage1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) holder.img1.getLayoutParams();
        params1.width = 2 * width + margin;
        params1.height = 2 * width + margin;
        holder.img1.setLayoutParams(params1);
        RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) holder.img2.getLayoutParams();
        params2.width = width;
        params2.height = width;
        holder.img2.setLayoutParams(params2);
        RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) holder.img3.getLayoutParams();
        params3.width = width;
        params3.height = width;
        holder.img3.setLayoutParams(params3);
        holder.name.setText(list.get(position).getShop_name());
        holder.slogan.setText(list.get(position).getIntroduce());
        holder.itemName.setText(list.get(position).getItem_name());
        holder.itemPrice.setText("¥"+list.get(position).getPrice());
        GlideApp.with(context).load(BASE_URL + list.get(position).getProtrait()).placeholder(R.drawable.img_default).override(50, 50).into(holder.avatar);
        holder.avatar.setOnClickListener(v -> {
            Intent intent =new Intent(context, ShopIndexActivity.class);
            intent.putExtra(AppConstant.SHOP_ID,list.get(position).getShop_id());
            context.startActivity(intent);
        });
        switch (list.get(position).getImages().size()) {
            case 0:
                GlideApp.with(context).load(R.drawable.img_default).override(200, 200).into(holder.img1);
                GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(holder.img2);
                GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(holder.img3);
                holder.img1.setOnClickListener(null);
                holder.img2.setOnClickListener(null);
                holder.img3.setOnClickListener(null);
                break;
            case 1:
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(0)).override(200, 200).into(holder.img1);
                GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(holder.img2);
                GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(holder.img3);
                holder.img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                holder.img2.setOnClickListener(null);
                holder.img3.setOnClickListener(null);
                break;
            case 2:
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(0)).override(200, 200).into(holder.img1);
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(1)).override(100, 100).into(holder.img2);
                holder.img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                holder.img2.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                holder.img3.setOnClickListener(null);
                break;
            case 3:
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(0)).placeholder(R.drawable.img_default).override(200, 200).into(holder.img1);
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(1)).placeholder(R.drawable.img_default).override(100, 100).into(holder.img2);
                GlideApp.with(context).load(BASE_URL + list.get(position).getImages().get(2)).placeholder(R.drawable.img_default).override(100, 100).into(holder.img3);
                holder.img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                holder.img2.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                holder.img3.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getItem_id()));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        RoundedImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.slogan)
        TextView slogan;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
