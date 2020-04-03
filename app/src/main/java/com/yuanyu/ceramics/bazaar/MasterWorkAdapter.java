package com.yuanyu.ceramics.bazaar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MasterWorkAdapter extends RecyclerView.Adapter<MasterWorkAdapter.ViewHolder> {

    private Context context;
    private List<MasterWorkBean> list;

    MasterWorkAdapter(Context context, List<MasterWorkBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_master_work, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .override(200,200)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("¥"+list.get(position).getPrice());
        holder.location.setText(list.get(position).getMaster_name());
        holder.itemView.setOnClickListener(v -> ItemDetailAcitivity.actionStart(context, list.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.item_img)
        RoundedImageView image;
        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.location)
        TextView location;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
