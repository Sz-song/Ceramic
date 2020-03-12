package com.yuanyu.ceramics.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ZuopinAdapter extends RecyclerView.Adapter<ZuopinAdapter.ViewHolder> {
    private List<Zuopin> mZuopinList;
    private Context mContext;

    ZuopinAdapter(List<Zuopin> mZuopinList, Context mContext) {
        this.mZuopinList = mZuopinList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_adscell, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Zuopin zuopin = mZuopinList.get(position);
        GlideApp.with(mContext)
                .load(BASE_URL + zuopin.getImage())
                .placeholder(R.drawable.img_default)
                .into(holder.itemImg);
        holder.itemName.setText(zuopin.getName());
        holder.price.setText("￥"+zuopin.getPrice() + "");
        holder.location.setText(zuopin.getLocation());
        holder.itemView.setOnClickListener(view -> ItemDetailAcitivity.actionStart(mContext, mZuopinList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mZuopinList.size();
    }


    static class ViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        RoundedImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
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
