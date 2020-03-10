package com.yuanyu.ceramics.seller.delivery;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;


public class CourierPayTypePopupWindow extends PopupWindow {
    private View view;
    private Button poster_pay,receiver_pay;
    private OnPositionClickListener positionClickListener;

    void setPositionClickListener(OnPositionClickListener positionClickListener) {
        this.positionClickListener = positionClickListener;
    }

    CourierPayTypePopupWindow(Context context, String type) {
        super(context);
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_courierpaytype, null);
        this.setContentView(this.view);
        this.poster_pay=view.findViewById(R.id.poster_pay);
        this.receiver_pay=view.findViewById(R.id.reviver_pay);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        if(type.equals("1")){//1-现付，2-到付
            poster_pay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            poster_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stpri));
            receiver_pay.setTextColor(context.getResources().getColor(R.color.lightGray));
            receiver_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stlightgray));
        }else {
            poster_pay.setTextColor(context.getResources().getColor(R.color.lightGray));
            poster_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stlightgray));
            receiver_pay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            receiver_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stpri));
        }
       this.setOnDismissListener(() -> {
           WindowManager.LayoutParams lp1 = ((Activity)context).getWindow().getAttributes();
           lp1.alpha = 1f; // 0.0-1.0
           ((Activity) context).getWindow().setAttributes(lp1);
       });
        poster_pay.setOnClickListener(view -> {
            poster_pay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            poster_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stpri));
            receiver_pay.setTextColor(context.getResources().getColor(R.color.lightGray));
            receiver_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stlightgray));
            positionClickListener.callback(0);
            dismiss();
        });
        receiver_pay.setOnClickListener(view -> {
            poster_pay.setTextColor(context.getResources().getColor(R.color.lightGray));
            poster_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stlightgray));
            receiver_pay.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            receiver_pay.setBackground(context.getResources().getDrawable(R.drawable.r5_sowhite_stpri));
            positionClickListener.callback(1);
            dismiss();
        });
    }
}
