package com.yuanyu.ceramics.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class SearchShopAdapter extends RecyclerView.Adapter<SearchShopAdapter.ViewHolder> {
    private Context context;
    private List<SearchShopBean> list;
    private OnNoDataListener listener;
    SearchShopAdapter(Context context, List<SearchShopBean> list) {
        this.context = context;
        this.list = list;
    }
    public void setListener(OnNoDataListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL+list.get(position).getPortrait())
                .placeholder(R.drawable.logo_default)
                .override(50,50)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName());
        holder.introduce.setText(list.get(position).getIntroduce());
        holder.enterStore.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShopIndexActivity.class);
            intent.putExtra("shopid", list.get(position).getShopid());
            context.startActivity(intent);
        });
        holder.recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(context,3));
        holder.recyclerview.setAdapter(new SearchShopItemAdapter(context,list.get(position).getList()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShopIndexActivity.class);
            intent.putExtra("shopid", list.get(position).getShopid());
            context.startActivity(intent);
        });
        listener.setNodata();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.introduce)
        TextView introduce;
        @BindView(R.id.enter_store)
        TextView enterStore;
        @BindView(R.id.linear)
        LinearLayout linear;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class SearchShopItemAdapter extends RecyclerView.Adapter<SearchShopItemAdapter.ViewHolder> {
        private Context context;
        private List<SearchShopBean.GoodsBean> item_list;

        SearchShopItemAdapter(Context context, List<SearchShopBean.GoodsBean> item_list) {
            this.context = context;
            this.item_list = item_list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_squareimageview, parent, false));
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (null == item_list.get(position)) {return; }
            GlideApp.with(context)
                    .load(BASE_URL + item_list.get(position).getImage())
                    .override(400, 400)
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL))
                    .placeholder(R.drawable.img_default)
                    .into(holder.squareimage);
            holder.squareimage.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,item_list.get(position).getItem_id()));
        }

        @Override
        public int getItemCount() {
            return item_list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            @BindView(R.id.squareimage)
            SquareImageView squareimage;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}
