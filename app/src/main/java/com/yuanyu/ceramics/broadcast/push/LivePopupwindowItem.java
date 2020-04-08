package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.OnStringCallback;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LivePopupwindowItem extends PopupWindow {
    private CircleImageView portrait;
    private TextView name;
    private TextView enterShop;
    private RecyclerView recyclerview;
    private Context context;
    private View view;
    private OnStringCallback onChangeItemListenner;

    public void setOnChangeItemListenner(OnStringCallback onChangeItemListenner) {
        this.onChangeItemListenner = onChangeItemListenner;
    }

    public LivePopupwindowItem(Context context, int type, List<LiveItemBean> list, String shop_id, String shop_name, String image) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_live_item, null);
        recyclerview=view.findViewById(R.id.recyclerview);
        portrait=view.findViewById(R.id.portrait);
        name=view.findViewById(R.id.name);
        enterShop=view.findViewById(R.id.enter_shop);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        Glide.with(context)
                .load(AppConstant.BASE_URL+image)
                .override(50,50)
                .placeholder(R.drawable.logo_default)
                .into(portrait);
        name.setText(shop_name);
        enterShop.setOnClickListener(view -> {
            Intent intent =new Intent(context, ShopIndexActivity.class);
            intent.putExtra(AppConstant.SHOP_ID,shop_id);
            context.startActivity(intent);
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        LiveItemAdapter adapter = new LiveItemAdapter(context, list, type);
        adapter.setOnChangeItemListenner(item_id -> onChangeItemListenner.callback(item_id));
        recyclerview.setAdapter(adapter);
    }
}
