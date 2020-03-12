package com.yuanyu.ceramics.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.R;

import java.util.List;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class PhotoDisplayAdapter extends RecyclerView.Adapter<PhotoDisplayAdapter.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public PhotoDisplayAdapter(Context context, List<String> list){
        mContext = context;
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_upload_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(BASE_URL+mList.get(position)).into(holder.ivImage);
        holder.cancel.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        ImageView cancel;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            cancel = itemView.findViewById(R.id.cancel);
        }
    }
}
