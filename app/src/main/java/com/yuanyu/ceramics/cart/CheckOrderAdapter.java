package com.yuanyu.ceramics.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
import com.yuanyu.ceramics.utils.L;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

/**
 * Created by Administrator on 2018-07-16.
 */

public class CheckOrderAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<GoodsBean> goodsList;
    private List<AdsCellBean> adsCellList;
    private List<AddressManageBean> addresslist;
    private AddressManageBean addressBean;
    private OnSetAddressLinster onSetAddressLinster;
    private OnPositionClickListener onSelectCouponsLinster;

    public void setOnSelectCouponsLinster(OnPositionClickListener onSelectCouponsLinster) {
        this.onSelectCouponsLinster = onSelectCouponsLinster;
    }

    public CheckOrderAdapter(Context context, List<GoodsBean> goodsList, List<AdsCellBean> adsCellList, List<AddressManageBean> addresslist) {
        this.context = context;
        this.goodsList = goodsList;
        this.adsCellList = adsCellList;
        this.addresslist = addresslist;
    }
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else if (position > 0 && position <= goodsList.size()) {
            return 1;
        } else if (position == goodsList.size() + 1) {
            return 2;
        } else {
            return 3;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_checkorder0, parent, false);
            return new ViewHolder0(view);
        } else if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_checkorder1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_checkorder2, parent, false);
            return new ViewHolder2(view);
        } else if (viewType == 3) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_adscell, parent, false);
            return new ViewHolder3(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder0) {
            if (null != addresslist && addresslist.size() > 0) {
                ((ViewHolder0) holder).noAddress.setVisibility(View.GONE);
                ((ViewHolder0) holder).changeAddress.setVisibility(View.VISIBLE);
                boolean beDefault =false;
                for(int i=0;i<addresslist.size();i++){
                    if(addresslist.get(i).getIsdefault()==1){
                        addressBean=addresslist.get(i);
                        beDefault=true;
                    }
                }
                if(!beDefault){
                    addressBean=addresslist.get(0);
                }
                if(null!=addressBean){
                    ((ViewHolder0) holder).name.setText(addressBean.getName());
                    ((ViewHolder0) holder).phone.setText(addressBean.getPhone());
                    ((ViewHolder0) holder).address.setText(addressBean.getProvince()+addressBean.getCity()+addressBean.getExparea()+addressBean.getAddress());
                }else{
                    ((ViewHolder0) holder).noAddress.setVisibility(View.VISIBLE);
                    ((ViewHolder0) holder).changeAddress.setVisibility(View.GONE);
                }
            } else {
                ((ViewHolder0) holder).noAddress.setVisibility(View.VISIBLE);
                ((ViewHolder0) holder).changeAddress.setVisibility(View.GONE);
            }
            holder.itemView.setOnClickListener(view -> onSetAddressLinster.setAddress());
        } else if (holder instanceof ViewHolder1) {
            if (goodsList.get(position - 1).getIstop()) {
                ((ViewHolder1) holder).cartHeader.setVisibility(View.VISIBLE);
            } else {
                ((ViewHolder1) holder).cartHeader.setVisibility(View.GONE);
            }
            if (position < goodsList.size()) {
                if (goodsList.get(position).getIstop()) {
                    ((ViewHolder1) holder).massage.setVisibility(View.VISIBLE);
                } else {
                    ((ViewHolder1) holder).massage.setVisibility(View.GONE);
                }
            }
            if (position == goodsList.size()) {
                ((ViewHolder1) holder).massage.setVisibility(View.VISIBLE);
            }
            if(goodsList.get(position-1).isCan_coupons()){
                ((ViewHolder1) holder).couponsLinear.setVisibility(View.VISIBLE);
                ((ViewHolder1) holder).coupons.setText(goodsList.get(position-1).getCoupons_txt());
                ((ViewHolder1) holder).couponsLinear.setOnClickListener(v -> onSelectCouponsLinster.callback(position-1));
            }else{
                ((ViewHolder1) holder).couponsLinear.setVisibility(View.GONE);
            }
            ((ViewHolder1) holder).studioName.setText(goodsList.get(position - 1).getShopname());
            ((ViewHolder1) holder).mingcheng.setText(goodsList.get(position - 1).getCommodityname());
            ((ViewHolder1) holder).price.setText("¥" + String.format("%.2f", goodsList.get(position - 1).getPrice()));
            GlideApp.with(context)
                    .load(BASE_URL + goodsList.get(position - 1).getShoplogo())
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder1) holder).studioImg);
            GlideApp.with(context)
                    .load(BASE_URL + goodsList.get(position - 1).getImage())
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder1) holder).itemImg);
            if(((ViewHolder1) holder).massageTxt.getTag()instanceof TextWatcher){
                ((ViewHolder1) holder).massageTxt.removeTextChangedListener((TextWatcher)(((ViewHolder1) holder).massageTxt.getTag()));
            }
            ((ViewHolder1) holder).massageTxt.setText(goodsList.get(position-1).getComment());
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    if (TextUtils.isEmpty(editable.toString())) {
                        goodsList.get(position-1).setComment("");
                    } else {
                        goodsList.get(position-1).setComment(editable.toString());
                        if (editable.toString().length()>139){
                            Toast.makeText(context, "字数不能超过140", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            };
            ((ViewHolder1) holder).massageTxt.addTextChangedListener(watcher);
            ((ViewHolder1) holder).massageTxt.setTag(watcher);
        } else if (holder instanceof ViewHolder2) {
            L.e("");
        } else if (holder instanceof ViewHolder3) {
            GlideApp.with(context)
                    .load(BASE_URL + adsCellList.get((position - (goodsList.size() + 2))).getImage())
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder3) holder).itemImg);
            ((ViewHolder3) holder).itemName.setText(adsCellList.get(position - (goodsList.size() + 2)).getName());
            ((ViewHolder3) holder).location.setText(adsCellList.get(position - (goodsList.size() + 2)).getLocation());
            ((ViewHolder3) holder).price.setText("¥" + adsCellList.get(position - (goodsList.size() + 2)).getPrice());
            holder.itemView.setOnClickListener(view -> ItemDetailAcitivity.actionStart(context, adsCellList.get(position - (goodsList.size() + 2)).getId()));
        }
    }
    @Override
    public int getItemCount() {
        return goodsList.size() + adsCellList.size() + 2;
    }
    public AddressManageBean getAddressBean(){
        return addressBean;
    }
    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.no_address)
        LinearLayout noAddress;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.phone)
        TextView phone;
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.change_address)
        RelativeLayout changeAddress;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.studio_img)
        CircleImageView studioImg;
        @BindView(R.id.studio_name)
        TextView studioName;
        @BindView(R.id.cart_header)
        LinearLayout cartHeader;
        @BindView(R.id.item_img)
        ImageView itemImg;
        @BindView(R.id.mingcheng)
        TextView mingcheng;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.massage_txt)
        EditText massageTxt;
        @BindView(R.id.massage)
        LinearLayout massage;
        @BindView(R.id.coupons)
        TextView coupons;
        @BindView(R.id.coupons_linear)
        LinearLayout couponsLinear;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
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

    public void setOnSetAddressLinster(OnSetAddressLinster onSetAddressLinster) {
        this.onSetAddressLinster = onSetAddressLinster;
    }

    interface OnSetAddressLinster {
        void setAddress();
    }



}

