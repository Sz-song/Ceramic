package com.yuanyu.ceramics.home.homepage;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.banner.CustomBanner;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.ItemBean;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class HomepageAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<String> bannerList;
    private List<ItemBean> list;


    public HomepageAdapter(Context context, List<String> bannerList, List<ItemBean> list) {
        this.context = context;
        this.bannerList = bannerList;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homepage, viewGroup, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homepage1, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            if (bannerList != null) {
                ((ViewHolder) holder).banner.setPages(new CustomBanner.ViewCreator<String>() {
                    @Override
                    public View createView(Context context, int position) {
                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        return imageView;
                    }
                    @Override
                    public void updateUI(Context context, View view, int position, String image) {
                        GlideApp.with(context)
                                .load(BASE_URL + image)
                                .placeholder(R.drawable.image_default)
                                .override(800, 400)
                                .into((ImageView) view);
                    }
                }, bannerList).startTurning(5000);
            }
        }else if(holder instanceof ViewHolder1){
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position - 1).getImage())
                    .placeholder(R.drawable.image_default)
                    .into(((ViewHolder1) holder).itemImg);
            ((ViewHolder1) holder).itemName.setText(list.get(position - 1).getName());
            ((ViewHolder1) holder).location.setText(list.get(position - 1).getLocation());
            ((ViewHolder1) holder).price.setText("¥" + list.get(position - 1).getPrice());
//            holder.itemView.setOnClickListener(view -> ItemDetailActivity.actionStart(context, adsCellList.get(position - (goodsList.size() + 2)).getId()));

        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        CustomBanner banner;
        @BindView(R.id.cooperate)
        ImageView cooperate;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder  {
        @BindView(R.id.item_img)
        RoundedImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.location)
        TextView location;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
