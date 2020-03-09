package com.yuanyu.ceramics.seller.liveapply;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

public class LiveApplyTypePopupWindow extends PopupWindow {

    private TextView meiyu,yushuo,taoshi,cancel;
    private Context context;
    private OnPositionClickListener positionClickListener;

    public void setPositionClickListener(OnPositionClickListener positionClickListener) {
        this.positionClickListener = positionClickListener;
    }

    public LiveApplyTypePopupWindow(Context context) {
        super(context);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_live_apply_type, null);
        this.setContentView(view);
        meiyu= view.findViewById(R.id.meiyu);
        yushuo= view.findViewById(R.id.yushuo);
        taoshi= view.findViewById(R.id.taoshi);
        cancel= view.findViewById(R.id.cancel);
        this.setOutsideTouchable(true);//外部点击消失
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        this.cancel.setOnClickListener(v -> dismiss());
        this.meiyu.setOnClickListener(v -> positionClickListener.callback(2));
        this.yushuo.setOnClickListener(v -> positionClickListener.callback(4));
        this.taoshi.setOnClickListener(v -> positionClickListener.callback(3));
    }

    public void show(View parent, int gravity, int x, int y) {
        this.showAtLocation(parent,gravity,x,y);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
