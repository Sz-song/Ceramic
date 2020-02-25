package com.yuanyu.ceramics.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


/**
 * Created by Administrator on 2018-08-02.
 */

public class SquareImageViewAdapter extends RecyclerView.Adapter<SquareImageViewAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    private boolean isCorners=true;
    public SquareImageViewAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public SquareImageViewAdapter(Context context, List<String> list, boolean isCorners) {
        this.context = context;
        this.list = list;
        this.isCorners = isCorners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_squareimageview, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (null == list.get(position) || list.get(position).equals("")) {
            return;
        }
        if(isCorners) {
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position))
                    .override(400, 400)
                    .transforms(new CenterCrop(), new RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL))
                    .placeholder(R.drawable.img_default)
                    .into(holder.squareimage);
        }else{
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position))
                    .override(400, 400)
                    .placeholder(R.drawable.img_default)
                    .into(holder.squareimage);
        }
//        holder.squareimage.setOnClickListener(view -> ViewImageActivity.actionStart(context,position, (ArrayList) list));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.squareimage)
        SquareImageView squareimage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
