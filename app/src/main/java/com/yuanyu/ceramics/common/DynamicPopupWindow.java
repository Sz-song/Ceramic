package com.yuanyu.ceramics.common;

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
public class DynamicPopupWindow extends PopupWindow {
    private int reporttype;
    private OnPositionClickListener yubaDeleteListener;
    private OnPositionClickListener yubaShieldingListener;
    private OnPositionClickListener blacklistListener;

    public void setYubaShieldingListener(OnPositionClickListener yubaShieldingListener) {
        this.yubaShieldingListener = yubaShieldingListener;
    }

    public void setBlacklistListener(OnPositionClickListener blacklistListener) {
        this.blacklistListener = blacklistListener;
    }

    public void setYubaDeleteListener(OnPositionClickListener yubaDeleteListener) {
        this.yubaDeleteListener = yubaDeleteListener;
    }

    public DynamicPopupWindow(final Context context, final int type, int kind, final int position, String id, boolean isindex) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_yuba_delete, null);
        TextView delete = view.findViewById(R.id.delete);
        TextView shielding = view.findViewById(R.id.shield);
        TextView blacklist = view.findViewById(R.id.blacklist);
        TextView cancel = view.findViewById(R.id.cancel);
        TextView report = view.findViewById(R.id.report);
        switch (kind){
            case 0://动态
                delete.setText("删除本条动态");
                shielding.setText("屏蔽此条动态");
                blacklist.setText("拉黑发布者");
                report.setText("举报此动态");
                reporttype=0;
                break;
            case 1://文章
                delete.setText("删除本篇文章");
                shielding.setText("屏蔽此篇文章");
                blacklist.setText("拉黑作者");
                report.setText("举报此文章");
                reporttype=3;
                break;
            case 2://评论
                delete.setText("删除此评论");
                shielding.setText("屏蔽此评论");
                blacklist.setText("拉黑发布者");
                report.setText("举报此评论");
                reporttype=4;
                break;
        }
        if(type==0){//自己看
            delete.setVisibility(View.VISIBLE);
            shielding.setVisibility(View.GONE);
            blacklist.setVisibility(View.GONE);
            report.setVisibility(View.GONE);
        }else if(type==1){//别人看
            delete.setVisibility(View.GONE);
            shielding.setVisibility(View.VISIBLE);
            blacklist.setVisibility(View.VISIBLE);
            report.setVisibility(View.VISIBLE);
            if(isindex){
                shielding.setVisibility(View.GONE);
            }
        }else if(type==2){//别人看店铺动态动态
            delete.setVisibility(View.GONE);
            shielding.setVisibility(View.GONE);
            blacklist.setVisibility(View.GONE);
            report.setVisibility(View.VISIBLE);
        }
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        delete.setOnClickListener(view1 -> yubaDeleteListener.callback(position));
        shielding.setOnClickListener(view1 -> yubaShieldingListener.callback(position));
        blacklist.setOnClickListener(view1-> blacklistListener.callback(position));
        cancel.setOnClickListener(view1 -> dismiss());
        report.setOnClickListener(view1 -> {
//            ReportActivity.actionStart(context,id,reporttype);
            dismiss();
        });
    }
}
