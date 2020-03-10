package com.yuanyu.ceramics.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoVedioAdapter extends RecyclerView.Adapter<PhotoVedioAdapter.ViewHolder> {
    private Context context;
    private List<PhotoVideoBean> list;
    private boolean delete;
    private OnPositionClickListener onItemClickListener;
    private OnPositionClickListener onDeteleClickListener;

    public void setOnItemClickListener(OnPositionClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnDeteleClickListener(OnPositionClickListener onDeteleClickListener) {
        this.onDeteleClickListener = onDeteleClickListener;
    }

    public PhotoVedioAdapter(Context context, List<PhotoVideoBean> list, boolean delete) {
        this.context = context;
        this.list = list;
        this.delete =delete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_photovideo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(list.get(position).getType()==0||list.get(position).getType()==1){
            GlideApp.with(context)
                    .load(Integer.parseInt(list.get(position).getUrl()))
                    .placeholder(R.drawable.img_default)
                    .into(holder.image);
        }else {
            if(list.get(position).getUrl().startsWith("img/")){
                GlideApp.with(context)
                        .load(AppConstant.BASE_URL+list.get(position).getUrl())
                        .override(100,100)
                        .placeholder(R.drawable.img_default)
                        .into(holder.image);
            }else {
                GlideApp.with(context)
                        .load(list.get(position).getUrl())
                        .override(100,100)
                        .placeholder(R.drawable.img_default)
                        .into(holder.image);
            }

        }
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.callback(position);
        });
        holder.delete.setOnClickListener(view -> {
            onDeteleClickListener.callback(position);
        });
        if(list.get(position).getType()==0){//0 添加图片  1 添加视频 2 图片 3 视频
            holder.delete.setVisibility(View.GONE);
            holder.playVideo.setVisibility(View.GONE);
        }else if(list.get(position).getType()==1) {
            holder.delete.setVisibility(View.GONE);
            holder.playVideo.setVisibility(View.GONE);
        }else if(list.get(position).getType()==2) {
            if(delete){
                holder.delete.setVisibility(View.VISIBLE);
            }else {
                holder.delete.setVisibility(View.GONE);
            }
            holder.playVideo.setVisibility(View.GONE);
        }else if(list.get(position).getType()==3) {
            if(delete){
                holder.delete.setVisibility(View.VISIBLE);
            }else {
                holder.delete.setVisibility(View.GONE);
            }
            holder.playVideo.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        SquareImageView image;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.play_video)
        ImageView playVideo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
