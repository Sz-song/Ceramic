package com.yuanyu.ceramics.home.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.bazaar.BazaarActivity;
import com.yuanyu.ceramics.common.BannerBean;
import com.yuanyu.ceramics.common.view.custombanner.CustomBanner;
import com.yuanyu.ceramics.cooperation.CooperationActivity;
import com.yuanyu.ceramics.dingzhi.MyDingzhiActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.meet_master.MeetMasterActivity;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class FaxianAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<BannerBean> bannerList;
    private List<FaxianItemBean> list;
    private int width, margin;

    FaxianAdapter(Context context, List<BannerBean> bannerList, List<FaxianItemBean> list, int width, int margin) {
        this.context = context;
        this.bannerList = bannerList;
        this.list = list;
        this.width = width;
        this.margin = margin;
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
            return new ViewHolder0(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homepage, viewGroup, false));
        } else {
            return new ViewHolder1(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homepage1, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder0) {
            if (bannerList != null) {
                ((ViewHolder0) holder).banner.setPages(new CustomBanner.ViewCreator<BannerBean>() {
                    @Override
                    public View createView(Context context, int position) {
                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        return imageView;
                    }

                    @Override
                    public void updateUI(Context context, View view, int position, BannerBean bannerBean) {
                        GlideApp.with(context)
                                .load(BASE_URL + bannerBean.getImage())
                                .placeholder(R.drawable.img_default)
                                .override(800, 400)
                                .into((ImageView) view);
                    }
                }, bannerList).startTurning(5000);
            }
            ((ViewHolder0) holder).customization.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyDingzhiActivity.class);
                context.startActivity(intent);
            });
            ((ViewHolder0) holder).storeCenter.setOnClickListener(v -> {
                L.e("123");
                Intent intent = new Intent(context, BazaarActivity.class);
                context.startActivity(intent);
            });
            ((ViewHolder0) holder).meetMaster.setOnClickListener(v -> {
                L.e("123");
                Intent intent = new Intent(context, MeetMasterActivity.class);
                context.startActivity(intent);
            });
            ((ViewHolder0) holder).cooperation.setOnClickListener(v -> {
                L.e("123");
                Intent intent = new Intent(context, CooperationActivity.class);
                context.startActivity(intent);
            });
        } else if (holder instanceof ViewHolder1) {
            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) ((ViewHolder1) holder).img1.getLayoutParams();
            params1.width = 2 * width + margin;
            params1.height = 2 * width + margin;
            ((ViewHolder1) holder).img1.setLayoutParams(params1);
            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) ((ViewHolder1) holder).img2.getLayoutParams();
            params2.width = width;
            params2.height = width;
            ((ViewHolder1) holder).img2.setLayoutParams(params2);
            RelativeLayout.LayoutParams params3 = (RelativeLayout.LayoutParams) ((ViewHolder1) holder).img3.getLayoutParams();
            params3.width = width;
            params3.height = width;
            ((ViewHolder1) holder).img3.setLayoutParams(params3);
            ((ViewHolder1) holder).name.setText(list.get(position - 1).getShop_name());
            ((ViewHolder1) holder).slogan.setText(list.get(position - 1).getIntroduce());
            ((ViewHolder1) holder).itemName.setText(list.get(position-1).getItem_name());
            ((ViewHolder1) holder).itemPrice.setText("¥"+list.get(position-1).getPrice());
            GlideApp.with(context).load(BASE_URL + list.get(position - 1).getProtrait()).placeholder(R.drawable.img_default).override(50, 50).into(((ViewHolder1) holder).avatar);
            ((ViewHolder1) holder).avatar.setOnClickListener(v -> {
                Intent intent =new Intent(context, ShopIndexActivity.class);
                intent.putExtra(AppConstant.SHOP_ID,list.get(position).getShop_id());
                context.startActivity(intent);
            });
            switch (list.get(position - 1).getImages().size()) {
                case 0:
                    GlideApp.with(context).load(R.drawable.img_default).override(200, 200).into(((ViewHolder1) holder).img1);
                    GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img2);
                    GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img3);
                    ((ViewHolder1) holder).img1.setOnClickListener(null);
                    ((ViewHolder1) holder).img2.setOnClickListener(null);
                    ((ViewHolder1) holder).img3.setOnClickListener(null);
                    break;
                case 1:
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(0)).override(200, 200).into(((ViewHolder1) holder).img1);
                    GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img2);
                    GlideApp.with(context).load(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img3);
                    ((ViewHolder1) holder).img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    ((ViewHolder1) holder).img2.setOnClickListener(null);
                    ((ViewHolder1) holder).img3.setOnClickListener(null);
                    break;
                case 2:
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(0)).override(200, 200).into(((ViewHolder1) holder).img1);
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(1)).override(100, 100).into(((ViewHolder1) holder).img2);
                    ((ViewHolder1) holder).img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    ((ViewHolder1) holder).img2.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    ((ViewHolder1) holder).img3.setOnClickListener(null);
                    break;
                case 3:
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(0)).placeholder(R.drawable.img_default).override(200, 200).into(((ViewHolder1) holder).img1);
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(1)).placeholder(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img2);
                    GlideApp.with(context).load(BASE_URL + list.get(position - 1).getImages().get(2)).placeholder(R.drawable.img_default).override(100, 100).into(((ViewHolder1) holder).img3);
                    ((ViewHolder1) holder).img1.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    ((ViewHolder1) holder).img2.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    ((ViewHolder1) holder).img3.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context,list.get(position-1).getItem_id()));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.banner)
        CustomBanner banner;
        @BindView(R.id.customization)
        LinearLayout customization;
        @BindView(R.id.store_center)
        LinearLayout storeCenter;
        @BindView(R.id.meet_master)
        LinearLayout meetMaster;
        @BindView(R.id.cooperation)
        LinearLayout cooperation;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        RoundedImageView avatar;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.slogan)
        TextView slogan;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_price)
        TextView itemPrice;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
