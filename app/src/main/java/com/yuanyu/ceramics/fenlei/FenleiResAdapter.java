package com.yuanyu.ceramics.fenlei;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class FenleiResAdapter extends RecyclerView.Adapter<FenleiResAdapter.ViewHolder> {
    private List<FenLeiResBean> list;
    private Context context;
    public FenleiResAdapter(List<FenLeiResBean> list){this.list=list;}
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.fenle_result_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FenLeiResBean resultBean = list.get(position);
        GlideApp.with(context)
                .load(BASE_URL + resultBean.getImage())
                .placeholder(R.drawable.img_default)
                .into(holder.itemImg);
        holder.itemName.setText(resultBean.getName());
        holder.itemPrice.setText("¥" + resultBean.getPrice());
        holder.itemChandi.setText(resultBean.getLocation());
        holder.itemImg.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context, list.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.item_img)
        RoundedImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_chandi)
        TextView itemChandi;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
