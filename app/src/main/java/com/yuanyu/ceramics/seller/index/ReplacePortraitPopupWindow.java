package com.yuanyu.ceramics.seller.index;


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

/**
 * Created by cat on 2018/8/29.
 */

public class ReplacePortraitPopupWindow extends PopupWindow {
    private View view;
    private Context context;
    private Button selectPic,cancel,change_shop_introduce;
    public ReplacePortraitPopupWindow(Context context) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_replacepic, null);
        this.setContentView(this.view);
        this.cancel = view.findViewById(R.id.cancel);
        this.selectPic  =view .findViewById(R.id.select_pic);
        this.change_shop_introduce =view .findViewById(R.id.change_shop_introduce);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setOutsideTouchable(true);//外部点击消失
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = ((Activity)context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        this.cancel.setOnClickListener(view -> dismiss());
    }
    public void setPortraitClickListener(View.OnClickListener listener){
        selectPic.setOnClickListener(listener);
    }
    public void setIntroduceClickListener(View.OnClickListener listener){
        change_shop_introduce.setOnClickListener(listener);
    }
}
