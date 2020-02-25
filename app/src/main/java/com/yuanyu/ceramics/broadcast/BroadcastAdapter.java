package com.yuanyu.ceramics.broadcast;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder> {
    private Context context;
    private List<BroadcastBean> list;

    BroadcastAdapter(Context context, List<BroadcastBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_zhibogridview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.watchNum.setText("观看人数"+list.get(position).getWatch());
        holder.shopName.setText(list.get(position).getShop());
        holder.name.setText(list.get(position).getName());
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        RoundedImageView image;
        @BindView(R.id.shop_name)
        TextView shopName;
        @BindView(R.id.suscribe)
        TextView suscribe;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.watch_num)
        TextView watchNum;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
