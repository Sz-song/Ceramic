package com.yuanyu.ceramics.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {
    private Context context;
    private List<SearchItemBean> list;
    private OnNoDataListener listener;
    SearchItemAdapter(Context context, List<SearchItemBean> list) {
        this.context = context;
        this.list = list;
    }

    public void setListener(OnNoDataListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_search_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(v -> ItemDetailAcitivity.actionStart(context,list.get(position).getId()));
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .override(150,150)
                .placeholder(R.drawable.img_default)
                .into(holder.itemImg);
        holder.itemName.setText(list.get(position).getName());
        holder.price.setText("¥"+list.get(position).getPrice());
        listener.setNodata();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        SquareImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
