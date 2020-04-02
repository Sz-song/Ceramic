package com.yuanyu.ceramics.broadcast.pull;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanyu.ceramics.R;

public class LivePopupWindowAddPrice extends PopupWindow {
    private Context context;
    private View view;
    private TextView reducePrice,nowPrice,addPrice,offer;
    private float price, add_price;
    LivePopupWindowAddPrice(Context context, float price, float add_price) {
        super(context);
        this.context = context;
        this.price=price;
        this.add_price=add_price;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_live_add_price, null);
        reducePrice=view.findViewById(R.id.reduce_price);
        nowPrice=view.findViewById(R.id.now_price);
        addPrice=view.findViewById(R.id.add_price);
        offer=view.findViewById(R.id.offer);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        reducePrice.setOnClickListener(view -> {

        });
        addPrice.setOnClickListener(view -> {

        });
        offer.setOnClickListener(view -> {

        });
    }

}
