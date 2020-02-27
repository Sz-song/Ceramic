package com.yuanyu.ceramics.order;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private Context mContext;
    private List<ItemCellBean> mList;
    private boolean canRefund;
    private RefundListener refundListener;
    private ClickListener clickListener;

    ItemAdapter(Context context, List<ItemCellBean> list, boolean canRefund) {
        mContext = context;
        mList = list;
        this.canRefund = canRefund;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setRefundListener(RefundListener refundListener) {
        this.refundListener = refundListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        GlideApp.with(mContext)
                .load(BASE_URL + mList.get(position).getImage())
                .override(200, 200)
                .placeholder(R.drawable.img_default)
                .into(holder.itemImage);
        holder.itemName.setText(mList.get(position).getName());
        holder.itemPrice.setText("¥" + mList.get(position).getPrice());
        holder.priceOld.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
        holder.priceOld.setText("¥" + mList.get(position).getOriginal_price());
        if (mList.get(position).getNum() > 0) {
            holder.num.setText("x" + mList.get(position).getNum());
        }
        if (canRefund) holder.itemRefund.setVisibility(View.VISIBLE);
        else holder.itemRefund.setVisibility(View.GONE);
        holder.itemRefund.setOnClickListener(view -> refundListener.refund(position));
        if (clickListener != null)
            holder.itemView.setOnClickListener(view -> clickListener.click());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface RefundListener {
        void refund(int itemPostition);
    }

    public interface ClickListener {
        void click();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_image)
        ImageView itemImage;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.price_old)
        TextView priceOld;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.num)
        TextView num;
        @BindView(R.id.item_refund)
        TextView itemRefund;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
