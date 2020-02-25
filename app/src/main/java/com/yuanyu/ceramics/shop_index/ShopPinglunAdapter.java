package com.yuanyu.ceramics.shop_index;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.SquareImageViewAdapter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.HelpUtils;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class ShopPinglunAdapter extends RecyclerView.Adapter<ShopPinglunAdapter.ViewHolder> {
    private List<EvaluationManageBean> list;
    private Context context;


    public ShopPinglunAdapter(Context context, List<EvaluationManageBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shoppinglun_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .override(100,100)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.time.setText(TimeUtils.CountTime(list.get(position).getTime()));
        holder.productName.setText(list.get(position).getProductname());
        holder.evaleation.setText(list.get(position).getEvaleation());
        if (list.get(position).getReply_txt().length() > 0) {
            holder.replyTxt.setVisibility(View.VISIBLE);
            holder.replyTxt.setText("掌柜回复：" + list.get(position).getReply_txt());
        } else {
            holder.replyTxt.setVisibility(View.GONE);
        }
        if (null != list.get(position).getPic_list() && list.get(position).getPic_list().size() > 0) {
            holder.imgRecycle.setVisibility(View.VISIBLE);
            CantScrollGirdLayoutManager manager = new CantScrollGirdLayoutManager(context, 3);
            holder.imgRecycle.setLayoutManager(manager);
            SquareImageViewAdapter adapter = new SquareImageViewAdapter(context, list.get(position).getPic_list());
            holder.imgRecycle.setAdapter(adapter);
        } else {
            holder.imgRecycle.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.evaleation)
        TextView evaleation;
        @BindView(R.id.img_recycle)
        RecyclerView imgRecycle;
        @BindView(R.id.reply_txt)
        TextView replyTxt;
        @BindView(R.id.item)
        LinearLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
