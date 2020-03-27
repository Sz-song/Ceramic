package com.yuanyu.ceramics.center_circle.release;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class GetFriImgAdapter extends RecyclerView.Adapter<GetFriImgAdapter.ViewHolder> {
    private Context context;
    private List<FriendBean> list;
    @NonNull
    @Override
    public GetFriImgAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_checkimg,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    public GetFriImgAdapter(Context context, List<FriendBean> list){
        this.context = context;
        this.list = list;
    }
    @Override
    public void onBindViewHolder(@NonNull GetFriImgAdapter.ViewHolder holder, int position) {
        GlideApp.with(context)
                .load(BASE_URL + list.get(position).getPortrait())
                .placeholder(R.drawable.img_default)
                .transforms(new CenterCrop(), new RoundedCornersTransformation(10, 0, RoundedCornersTransformation.CornerType.ALL))
                .into(holder.portrait);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        ImageView portrait;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
