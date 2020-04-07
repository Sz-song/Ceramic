package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
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
    private OnPositionClickListener onChangeItemListenner;

    public void setOnChangeItemListenner(OnPositionClickListener onChangeItemListenner) {
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
        holder.name.setText(list.get(position).getName());
        GlideApp.with(context).load(BASE_URL+list.get(position).getImage()).override(100, 100).into(holder.image);
        holder.price.setText("¥"+list.get(position).getPrice());
        if (type == 0){
            holder.button.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.button.setBackground(context.getResources().getDrawable(R.drawable.r10_stpri_sowhite));
            holder.button.setText("立即购买");
            holder.button.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position).getId()));
        }else {
            if(position==0){
                holder.button.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                holder.button.setBackground(context.getResources().getDrawable(R.drawable.r10_stpri_sowhite));
                holder.button.setText("已在当前");
                holder.button.setOnClickListener(view -> Toast.makeText(context, "已在当前", Toast.LENGTH_SHORT).show());
            }else{
                holder.button.setTextColor(context.getResources().getColor(R.color.white));
                holder.button.setBackground(context.getResources().getDrawable(R.drawable.r10_sopri));
                holder.button.setText("显示当前");
                holder.button.setOnClickListener(view -> {
//                    LiveItemBean beantop=list.get(position);
//                    LiveItemBean beanbot=list.get(0);
//                    list.remove(position);
//                    list.remove(0);
//                    list.add(0,beantop);
//                    list.add(beanbot);
//                    notifyDataSetChanged();
                    onChangeItemListenner.callback(position);
                });
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        SquareImageView image;
        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.item_price)
        TextView price;
        @BindView(R.id.item_button)
        Button button;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
