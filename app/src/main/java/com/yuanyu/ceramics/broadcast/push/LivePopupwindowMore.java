package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnNoDataListener;

public class LivePopupwindowMore extends PopupWindow {
    private Context context;
    private View view;
    private TextView share;
    private TextView report;
    private OnNoDataListener onShareListener;

    public void setOnShareListener(OnNoDataListener onShareListener) {
        this.onShareListener = onShareListener;
    }

    LivePopupwindowMore(Context context) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_live_more, null);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        share=view.findViewById(R.id.share);
        report=view.findViewById(R.id.report);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        share.setOnClickListener(view -> onShareListener.setNodata());
        report.setOnClickListener(view -> {
            Toast.makeText(context, "举报", Toast.LENGTH_SHORT).show();
            //Todo
        });
    }
}
