package com.yuanyu.ceramics.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.io.File;
import java.util.ArrayList;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class UploadPhotoAdapter extends RecyclerView.Adapter<UploadPhotoAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mList;
    private LayoutInflater mInflater;
    private boolean showCancel = true;
    public ItemClickListener mListener;
    public CancelListener cancelListener;

    public UploadPhotoAdapter(Context context){
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }
    public UploadPhotoAdapter(Context context,ArrayList<String> list){
        mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        mList = list;
    }

    public void setShowCancel(boolean showCancel) {
        this.showCancel = showCancel;
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.mListener = listener;
    }

    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public ArrayList<String> getImages() {
        return mList;
    }
    public void refresh(ArrayList<String> list){
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_upload_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final String image = mList.get(position);
        if (image.contains("add_pic")){
            String sub = image.substring(7);
            int id = Integer.parseInt(sub);
            GlideApp.with(mContext)
                    .load(id)
                    .into(holder.ivImage);
            holder.cancel.setVisibility(View.GONE);
        }
        else if (image.contains("img/")){
            GlideApp.with(mContext)
                    .load(BASE_URL+image)
                    .override(100,100)
                    .placeholder(R.drawable.img_default)
                    .into(holder.ivImage);
            holder.cancel.setVisibility(View.VISIBLE);
        }
        else {
            GlideApp.with(mContext)
                    .load(new File(image))
                    .override(100,100)
                    .placeholder(R.drawable.img_default)
                    .into(holder.ivImage);
            holder.cancel.setVisibility(View.VISIBLE);
        }
        if (!showCancel) holder.cancel.setVisibility(View.GONE);
        if (mListener != null) {
            //绑定点击事件
            holder.itemView.setOnClickListener(v -> {
                //把条目的位置回调回去
                mListener.onItemClick(position,holder.ivImage);
            });
        }
        if (cancelListener !=null){
            holder.cancel.setOnClickListener(view -> cancelListener.cancel(position));
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView cancel;
        ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            cancel = itemView.findViewById(R.id.cancel);
        }
    }

    public interface ItemClickListener{
        void onItemClick(int position, View view);
    }
    public interface CancelListener{
        void cancel(int position);
    }
}
