package com.yuanyu.ceramics.seller.liveapply;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class LiveApplyAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ItemBean> list;
    private boolean can_select=true;
    private OnNoDataListener onSelectClickListener;

    void setOnSelectClickListener(OnNoDataListener onSelectClickListener) {
        this.onSelectClickListener = onSelectClickListener;
    }

    public void setCan_select(boolean can_select) {
        this.can_select = can_select;
    }

    LiveApplyAdapter(Context context, List<ItemBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==list.size()&&can_select){return 0;}
        else{return 1;}
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ayyly_live0, parent, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_ayyly_live1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder0){
            GlideApp.with(context)
                    .load(R.drawable.add_item)
                    .into(((ViewHolder0) holder).image);
            holder.itemView.setOnClickListener(v -> onSelectClickListener.setNodata());
        }else if(holder instanceof ViewHolder1){
            GlideApp.with(context)
                    .load(AppConstant.BASE_URL+list.get(position).getImage())
                    .override(100,100)
                    .placeholder(R.drawable.img_default)
                    .transforms(new CenterCrop(), new RoundedCornersTransformation((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1.3f, context.getResources().getDisplayMetrics()), 0, RoundedCornersTransformation.CornerType.TOP))
                    .into(((ViewHolder1) holder).image);
            ((ViewHolder1) holder).name.setText(list.get(position).getItem());
            ((ViewHolder1) holder).price.setText("¥"+list.get(position).getPrice());
            if(can_select){
                ((ViewHolder1) holder).cancel.setVisibility(View.VISIBLE);
            }else{((ViewHolder1) holder).cancel.setVisibility(View.GONE);}
            ((ViewHolder1) holder).cancel.setOnClickListener(v -> {
                list.remove(position);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        if(can_select){
            return list.size()+1;
        }else{
            return list.size();
        }
    }

    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.cancel)
        ImageView cancel;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
