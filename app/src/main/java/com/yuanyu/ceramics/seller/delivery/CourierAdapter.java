package com.yuanyu.ceramics.seller.delivery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class CourierAdapter extends RecyclerView.Adapter<CourierAdapter.ViewHolder> {
    private Context context;
    private List<CourierBean> list;
    private OnPositionClickListener listener;

    public void setListener(OnPositionClickListener listener) {
        this.listener = listener;
    }

    public CourierAdapter(Context context, List<CourierBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_courier, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getPortrait())
                .override(200, 200)
                .placeholder(R.drawable.img_default)
                .into(holder.portrait);
        holder.name.setText(list.get(position).getName() + "    " + list.get(position).getId());
        if (list.get(position).getType() == 1) {
            holder.viewCover.setBackgroundColor(context.getResources().getColor(R.color.transparent));
            holder.introduce.setText(list.get(position).getIntroduce());
        } else if (list.get(position).getType() == 0) {
            holder.viewCover.setBackgroundColor(context.getResources().getColor(R.color.transparent_20));
            holder.introduce.setText("本地区暂不支持，敬请期待");
        }
        holder.itemView.setOnClickListener(view -> {
            if (list.get(position).getType() == 1) {
                listener.callback(position);
            } else {
                Toast.makeText(context, "本地区暂不支持，敬请期待", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        RoundedImageView portrait;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.introduce)
        TextView introduce;
        @BindView(R.id.view_cover)
        View viewCover;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
