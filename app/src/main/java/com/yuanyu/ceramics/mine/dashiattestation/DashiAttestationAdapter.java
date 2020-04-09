package com.yuanyu.ceramics.mine.dashiattestation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DashiAttestationAdapter extends RecyclerView.Adapter<DashiAttestationAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private OnPositionClickListener onPositionClickListener;
    DashiAttestationAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }
    void setOnPositionClickListener(OnPositionClickListener onPositionClickListener) {
        this.onPositionClickListener = onPositionClickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_content_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).contains("add_pic")){
            String sub = list.get(position).substring(7);
            int id = Integer.parseInt(sub);
            GlideApp.with(context)
                    .load(id)
                    .override(600,450)
                    .centerInside()
                    .into(holder.image);
        } else {
            GlideApp.with(context)
                    .load(new File(list.get(position)))
                    .override(300,300)
                    .placeholder(R.drawable.img_default)
                    .centerCrop()
                    .into(holder.image);
        }
        holder.image.setOnClickListener(view -> onPositionClickListener.callback(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        SquareImageView image;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
