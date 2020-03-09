package com.yuanyu.ceramics.seller.liveapply;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class SelectItemAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<ItemBean> mList;
    private Listener listener;
    private int mType;
    private boolean isCaster;
    private boolean canCancel = true;

    public SelectItemAdapter(Context context, List<ItemBean> list, int type, boolean isCaster ) {
        mContext = context;
        mList = list;
        mType = type;
        this.isCaster = isCaster;
    }

    public void setmList(List<ItemBean> list){
        mList = list;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_select_item, parent, false));
        }else if (viewType == 2){
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_select_item_2, parent, false));
        }else {
            return new ViewHolder3(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_select_item_3, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {
            Glide.with(mContext).load(BASE_URL + mList.get(position).getImage()).into(((ViewHolder1) holder).image);
            ((ViewHolder1) holder).name.setText(mList.get(position).getItem());
            ((ViewHolder1) holder).price.setText("¥" + mList.get(position).getPrice());
            ((ViewHolder1) holder).checkbox.setChecked(mList.get(position).isChecked());
            holder.itemView.setOnClickListener(view -> listener.onClick(holder, position));
            ((ViewHolder1) holder).checkbox.setOnClickListener(view -> listener.onClick(holder, position));
        }
        else if (holder instanceof ViewHolder2){
            Glide.with(mContext).load(BASE_URL + mList.get(position).getImage()).into(((ViewHolder2) holder).image);
            ((ViewHolder2) holder).name.setText(mList.get(position).getItem());
            ((ViewHolder2) holder).price.setText("¥" + mList.get(position).getPrice());
            if (isCaster){
                ((ViewHolder2) holder).detail.setVisibility(View.GONE);
                if (listener != null) holder.itemView.setOnClickListener(view -> listener.onClick(holder,position));
            } else {
                ((ViewHolder2) holder).detail.setVisibility(View.VISIBLE);
                ((ViewHolder2) holder).detail.setOnClickListener(view -> ItemDetailAcitivity.actionStart(mContext,mList.get(position).getId()));
            }
        }
        else if (holder instanceof ViewHolder3){
            Glide.with(mContext).load(BASE_URL + mList.get(position).getImage()).into(((ViewHolder3) holder).image);
            ((ViewHolder3) holder).name.setText(mList.get(position).getItem());
            ((ViewHolder3) holder).price.setText("¥" + mList.get(position).getPrice());
            if (canCancel) {
                ((ViewHolder3) holder).cancel.setOnClickListener(view -> {
                    mList.remove(position);
                    notifyDataSetChanged();
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_checkbox)
        SmoothCheckBox checkbox;
        @BindView(R.id.item_image)
        SquareImageView image;
        @BindView(R.id.item_name)
        TextView name;

        @BindView(R.id.item_price)
        TextView price;
        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface Listener{
        void onClick(RecyclerView.ViewHolder holder, int position);
    }

    public class ViewHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        SquareImageView image;
        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.item_price)
        TextView price;
        @BindView(R.id.detail)
        Button detail;

        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {

        @BindView(R.id.item_image)
        SquareImageView image;
        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.item_price)
        TextView price;
        @BindView(R.id.cancel)
        ImageView cancel;
        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
