package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.OnStringCallback;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class LiveItemAdapter extends RecyclerView.Adapter<LiveItemAdapter.ViewHolder> {
    private Context context;
    private List<LiveItemBean> list;
    private int type;//0 用户 1 商家
    private OnStringCallback onChangeItemListenner;

    public void setOnChangeItemListenner(OnStringCallback onChangeItemListenner) {
        this.onChangeItemListenner = onChangeItemListenner;
    }

    public LiveItemAdapter(Context context, List<LiveItemBean> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_live_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemName.setText(list.get(position).getName());
        GlideApp.with(context).load(BASE_URL + list.get(position).getImage()).override(100, 100).into(holder.itemImage);
        holder.itemPrice.setText("¥" + list.get(position).getPrice());
        if (type == 0) {
            holder.itemButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.itemButton.setBackground(context.getResources().getDrawable(R.drawable.r10_stpri_sowhite));
            holder.itemButton.setText("立即购买");
            holder.itemButton.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context, list.get(position).getId()));
        } else {
            if (position == 0) {
                holder.itemButton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                holder.itemButton.setBackground(context.getResources().getDrawable(R.drawable.r10_stpri_sowhite));
                holder.itemButton.setText("已在当前");
                holder.itemButton.setOnClickListener(view -> Toast.makeText(context, "已在当前", Toast.LENGTH_SHORT).show());
            } else {
                holder.itemButton.setTextColor(context.getResources().getColor(R.color.white));
                holder.itemButton.setBackground(context.getResources().getDrawable(R.drawable.r10_sopri));
                holder.itemButton.setText("显示当前");
                holder.itemButton.setOnClickListener(view -> {
                    LiveItemBean beantop=list.get(position);
                    LiveItemBean beanbot=list.get(0);
                    list.remove(position);
                    list.remove(0);
                    list.add(0,beantop);
                    list.add(beanbot);
                    notifyDataSetChanged();
                    onChangeItemListenner.callback(list.get(0).getId());
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_image)
        SquareImageView itemImage;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_button)
        TextView itemButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
