package com.yuanyu.ceramics.seller.refund.refund_detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnStringCallback;

public class RefundRejectPopupWindow extends PopupWindow {
    private View view;
    private EditText reject_reason;
    private TextView summit;
    private OnStringCallback onSummitClickListenner;
    public RefundRejectPopupWindow(Context context) {
        super(context);
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_reject_refund, null);
        this.reject_reason=view.findViewById(R.id.reject_reason);
        this.summit=view.findViewById(R.id.summit);
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
        ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = ((Activity)context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity)context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            ((Activity)context).getWindow().setAttributes(lp1);
        });
        reject_reason.setFocusable(true);
        reject_reason.setFocusableInTouchMode(true);
        summit.setOnClickListener(view -> {
            if(reject_reason.getText().toString().trim().length()>0) {
                onSummitClickListenner.callback(reject_reason.getText().toString());
            }else {
                Toast.makeText(context, "请输入拒绝理由", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setOnSummitClickListenner(OnStringCallback onSummitClickListenner) {
        this.onSummitClickListenner = onSummitClickListenner;
    }
}
