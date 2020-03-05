package com.yuanyu.ceramics.item;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.OnNoDataListener;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.common.SquareImageViewAdapter;
import com.yuanyu.ceramics.common.ViewImageActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ItemDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<AdsCellBean> adsCellList;
    private ItemDetailBean bean;
    private OnNoDataListener onShareClickListener;
    private OnNoDataListener onFocusMasterListener;

    public void setOnFocusMasterListener(OnNoDataListener onFocusMasterListener) {
        this.onFocusMasterListener = onFocusMasterListener;
    }

    public void setOnShareClickListener(OnNoDataListener onShareClickListener) {
        this.onShareClickListener = onShareClickListener;
    }

    public ItemDetailAdapter(Context context, ItemDetailBean bean, List<AdsCellBean> adsCellList) {
        this.context = context;
        this.bean = bean;
        this.adsCellList = adsCellList;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (null != bean.getItembean().getIntroducelist() && position > 0 && position <= bean.getItembean().getIntroducelist().size()) {
            return 1;
        } else if (null != bean.getItembean().getIntroducelist() && position == bean.getItembean().getIntroducelist().size() + 1) {
            return 3;
        } else {
            return 2;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false));
        } else if (viewType == 1) {
            return new ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_content_img1, parent, false));
        } else if (viewType == 2) {
            return new ViewHolder3(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_adscell, parent, false));
        } else if (viewType == 3) {
            return new ViewHolder4(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_content_txt1, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof ViewHolder1) {
            ((ViewHolder1) holder).length.setText(bean.getItembean().getLength() + "");
            ((ViewHolder1) holder).width.setText(bean.getItembean().getWidth() + "");
            ((ViewHolder1) holder).height.setText(bean.getItembean().getHeight() + "");
            if (bean.getItembean().getArtist() != null && bean.getItembean().getArtist().trim().length() > 0) {
                ((ViewHolder1) holder).artist.setText(bean.getItembean().getArtist());
            } else {
                ((ViewHolder1) holder).artist.setText("无");
            }
            ((ViewHolder1) holder).introduce.setText("" + bean.getText_introduce());
            ((ViewHolder1) holder).zhonglei.setText(bean.getItembean().getZhonglei());
            ((ViewHolder1) holder).pise.setText(bean.getItembean().getPise());
            ((ViewHolder1) holder).ticai.setText(bean.getItembean().getTicai());
            ((ViewHolder1) holder).chanzhuang.setText(bean.getItembean().getChanzhuang());
            ((ViewHolder1) holder).fenlei.setText(bean.getItembean().getFenlei());
            ((ViewHolder1) holder).weight.setText(bean.getItembean().getWeight() + "克");
            ((ViewHolder1) holder).serialNo.setText(bean.getItembean().getSerial_no());
//            ((ViewHolder1) holder).serialNo.setOnClickListener(view -> {
//                Intent intent = new Intent(context, BlockChainActivity.class);
//                intent.putExtra("serial_no", bean.getItembean().getSerial_no());
//                context.startActivity(intent);
//            });
            ((ViewHolder1) holder).goodsName.setText(bean.getItembean().getGoodsname());
            ((ViewHolder1) holder).goodsPrice.setText("¥" + String.format("%.2f", bean.getItembean().getGoodsprice()));

            ((ViewHolder1) holder).kucun.setText("库存: x" + bean.getStore_num());
            GlideApp.with(context)
                    .load(BASE_URL + bean.getStorebean().getStudioheadimg())
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder1) holder).storeImg);
            ((ViewHolder1) holder).storeName.setText(bean.getStorebean().getStorename());
            ((ViewHolder1) holder).yishouNum.setText(bean.getStorebean().getYishounum() + "");
            ((ViewHolder1) holder).pingfenNum.setText(bean.getStorebean().getPingfennum() + "");
            ((ViewHolder1) holder).guanzhuNum.setText(bean.getStorebean().getGuanzhunum() + "");
            ((ViewHolder1) holder).storeIntroduce.setText(bean.getStorebean().getIntroduce());
            if (bean.isIs_master()) {
                ((ViewHolder1) holder).goodsPrice.setTextColor(context.getResources().getColor(R.color.white));
                ((ViewHolder1) holder).goodsPriceRelat.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewHolder1) holder).masterCertification.setVisibility(View.VISIBLE);
                ((ViewHolder1) holder).masterStoreName.setText(bean.getStorebean().getStorename());
                ((ViewHolder1) holder).store.setVisibility(View.GONE);
                ((ViewHolder1) holder).master.setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(AppConstant.BASE_URL + bean.getMaster_img())
                        .override(80, 80)
                        .placeholder(R.drawable.img_default)
                        .into(((ViewHolder1) holder).masterImg);
                ((ViewHolder1) holder).masterName.setText(bean.getMaster_name());
                ((ViewHolder1) holder).masterTag.setText(bean.getMaster_tag());
                if (bean.isMaster_focus()) {
                    ((ViewHolder1) holder).masterFocus.setText("已关注");
                } else {
                    ((ViewHolder1) holder).masterFocus.setText("+ 关注");
                }
                ((ViewHolder1) holder).masterStoreIntroduce.setText(bean.getMaster_store_introduce());
                ((ViewHolder1) holder).masterRecyclerview.setLayoutManager(new CantScrollGirdLayoutManager(context, 4));
                SquareImageViewAdapter adapter = new SquareImageViewAdapter(context, bean.getMaster_achievement(), false);
                ((ViewHolder1) holder).masterRecyclerview.setAdapter(adapter);
                GlideApp.with(context).load(R.drawable.back_master)
                        .centerCrop()
                        .override(500, 500)
                        .placeholder(R.drawable.img_default)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ((ViewHolder1) holder).cellMaster.setBackground(resource);
                            }
                        });
                ((ViewHolder1) holder).masterFocus.setOnClickListener(v -> {
                    if(null!= Sp.getString(context,AppConstant.MOBILE)&&Sp.getString(context,AppConstant.MOBILE).length()>8) {
                        onFocusMasterListener.setNodata();
                    }else{
//                        Intent intent = new Intent(context, BindPhoneActivity.class);
//                        intent.putExtra("type",1);
//                        context.startActivity(intent);
                    }
                });
//                ((ViewHolder1) holder).enterMasterIndex.setOnClickListener(v -> PersonalIndexActivity.actionStart(context, bean.getMaster_id()));
            } else {
                ((ViewHolder1) holder).store.setVisibility(View.VISIBLE);
                ((ViewHolder1) holder).master.setVisibility(View.GONE);
                ((ViewHolder1) holder).goodsPrice.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewHolder1) holder).goodsPriceRelat.setBackgroundColor(context.getResources().getColor(R.color.white));
                ((ViewHolder1) holder).masterCertification.setVisibility(View.GONE);
            }
            ((ViewHolder1) holder).enterStore.setOnClickListener(view -> {
                Intent intent1 = new Intent(context, ShopIndexActivity.class);
                intent1.putExtra("shopid", bean.getStorebean().getShop_id() + "");
                L.e("shopid is" + bean.getStorebean().getShop_id());
                context.startActivity(intent1);
            });
            ((ViewHolder1) holder).store.setOnClickListener(view -> {
                Intent intent = new Intent(context, ShopIndexActivity.class);
                intent.putExtra("shopid", bean.getStorebean().getShop_id() + "");
                L.e("shopid is" + bean.getStorebean().getShop_id());
                context.startActivity(intent);
            });
            ((ViewHolder1) holder).share.setOnClickListener(view -> onShareClickListener.setNodata());

        } else if (holder instanceof ViewHolder2) {
            GlideApp.with(context)
                    .load(BASE_URL + bean.getItembean().getIntroducelist().get(position - 1))
                    .placeholder(R.drawable.img_default)
                    .override(400, 400)
                    .into(((ViewHolder2) holder).image);
            ((ViewHolder2) holder).image.setOnClickListener(view -> ViewImageActivity.actionStart(context, position - 1, (ArrayList) bean.getItembean().getIntroducelist()));
        } else if (holder instanceof ViewHolder3) {
            GlideApp.with(context)
                    .load(BASE_URL + adsCellList.get(position - (bean.getItembean().getIntroducelist().size() + 2)).getImage())
                    .placeholder(R.drawable.img_default)
                    .override(400, 400)
                    .into(((ViewHolder3) holder).itemImg);
            ((ViewHolder3) holder).itemName.setText(adsCellList.get(position - (bean.getItembean().getIntroducelist().size() + 2)).getName());
            ((ViewHolder3) holder).location.setText(adsCellList.get(position - (bean.getItembean().getIntroducelist().size() + 2)).getLocation());
            ((ViewHolder3) holder).price.setText("¥" + adsCellList.get(position - (bean.getItembean().getIntroducelist().size() + 2)).getPrice());
            holder.itemView.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context, adsCellList.get(position - (bean.getItembean().getIntroducelist().size() + 2)).getId()));
        }

    }

    @Override
    public int getItemCount() {
        if (null != bean.getItembean().getIntroducelist()) {
            return bean.getItembean().getIntroducelist().size() + adsCellList.size() + 2;
        } else {
            return adsCellList.size() + 3;
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        SquareImageView image;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_img)
        RoundedImageView itemImg;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.location)
        TextView location;

        ViewHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder4 extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;

        ViewHolder4(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    static class ViewHolder1 extends RecyclerView.ViewHolder  {
        @BindView(R.id.goods_price)
        TextView goodsPrice;
        @BindView(R.id.master_store_name)
        TextView masterStoreName;
        @BindView(R.id.master_certification)
        LinearLayout masterCertification;
        @BindView(R.id.goods_price_relat)
        RelativeLayout goodsPriceRelat;
        @BindView(R.id.goods_name)
        TextView goodsName;
        @BindView(R.id.serial_no_txt)
        TextView serialNoTxt;
        @BindView(R.id.serial_no)
        TextView serialNo;
        @BindView(R.id.kucun)
        TextView kucun;
        @BindView(R.id.share)
        ImageView share;
        @BindView(R.id.view_center)
        View viewCenter;
        @BindView(R.id.store_img)
        ImageView storeImg;
        @BindView(R.id.store_name)
        TextView storeName;
        @BindView(R.id.store_introduce)
        TextView storeIntroduce;
        @BindView(R.id.enter_store)
        TextView enterStore;
        @BindView(R.id.yishou)
        TextView yishou;
        @BindView(R.id.yishou_num)
        TextView yishouNum;
        @BindView(R.id.pingfen)
        TextView pingfen;
        @BindView(R.id.center)
        TextView center;
        @BindView(R.id.pingfen_num)
        TextView pingfenNum;
        @BindView(R.id.guanzhu_num)
        TextView guanzhuNum;
        @BindView(R.id.store)
        LinearLayout store;
        @BindView(R.id.master_img)
        CircleImageView masterImg;
        @BindView(R.id.master_name)
        TextView masterName;
        @BindView(R.id.master_tag)
        TextView masterTag;
        @BindView(R.id.master_focus)
        TextView masterFocus;
        @BindView(R.id.master_store_introduce)
        TextView masterStoreIntroduce;
        @BindView(R.id.master_recyclerview)
        RecyclerView masterRecyclerview;
        @BindView(R.id.enter_master_index)
        TextView enterMasterIndex;
        @BindView(R.id.cell_master)
        LinearLayout cellMaster;
        @BindView(R.id.master)
        LinearLayout master;
        @BindView(R.id.fenlei)
        TextView fenlei;
        @BindView(R.id.zhonglei)
        TextView zhonglei;
        @BindView(R.id.ticai)
        TextView ticai;
        @BindView(R.id.pise)
        TextView pise;
        @BindView(R.id.chanzhuang)
        TextView chanzhuang;
        @BindView(R.id.artist)
        TextView artist;
        @BindView(R.id.weight)
        TextView weight;
        @BindView(R.id.length)
        TextView length;
        @BindView(R.id.width)
        TextView width;
        @BindView(R.id.height)
        TextView height;
        @BindView(R.id.introduce)
        TextView introduce;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
