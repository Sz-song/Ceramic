package com.yuanyu.ceramics.cooperation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CooperationAdapter extends RecyclerView.Adapter<CooperationAdapter.ViewHolder> {
    private Context context;
    private List<CooperationListBean> list;

    CooperationAdapter(Context context, List<CooperationListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_cooperation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .override(100,100)
                .into(holder.background);
        holder.name.setText(list.get(position).getName());
        holder.introduce.setText(list.get(position).getIntroduce());
        holder.itemView.setOnClickListener(v -> {
            if(null!=list.get(position).getUrl()&&list.get(position).getUrl().length()>0){
//                Intent intent=new Intent(context,WebViewActivity.class);
//                intent.putExtra("webview_url",list.get(position).getUrl());
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.background)
        ImageView background;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.introduce)
        TextView introduce;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
