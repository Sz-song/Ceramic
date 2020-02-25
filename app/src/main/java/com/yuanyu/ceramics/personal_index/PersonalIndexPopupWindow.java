package com.yuanyu.ceramics.personal_index;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.home.HomeActivity;


public class PersonalIndexPopupWindow extends PopupWindow {
    private Context context;
    private String id;
    private int type;
    private View view;
    private ImageView index_img;
    private ImageView massage_img;
    private ImageView report_img;
    private ImageView share_img;
    private ImageView delete_img;
    private ImageView blacklist_img;
    private TextView index_txt;
    private TextView massage_txt;
    private TextView report_txt;
    private TextView share_txt;
    private TextView delete_txt;
    private TextView blacklist_txt;
    private LinearLayout index;
    private LinearLayout massage;
    private LinearLayout report;
    private LinearLayout share;
    private LinearLayout delete;
    private LinearLayout blacklist;
    public PersonalIndexPopupWindow(final Context context, final String id, final int type) {
        this.context = context;
        this.id=id;
        this.type=type;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_presonalindex, null);
        this.index_img=view.findViewById(R.id.index_img);
        this.massage_img=view.findViewById(R.id.massage_img);
        this.report_img=view.findViewById(R.id.report_img);
        this.share_img=view.findViewById(R.id.share_img);
        this.delete_img=view.findViewById(R.id.delete_img);
        this.blacklist_img=view.findViewById(R.id.blacklist_img);
        this.index_txt=view.findViewById(R.id.index_txt);
        this.massage_txt=view.findViewById(R.id.massage_txt);
        this.report_txt=view.findViewById(R.id.report_txt);
        this.share_txt=view.findViewById(R.id.share_txt);
        this.delete_txt=view.findViewById(R.id.delete_txt);
        this.blacklist_txt=view.findViewById(R.id.blacklist_txt);
        this.index=view.findViewById(R.id.index);
        this.massage=view.findViewById(R.id.massage);
        this.report=view.findViewById(R.id.report);
        this.share=view.findViewById(R.id.share);
        this.delete=view.findViewById(R.id.delete);
        this.blacklist=view.findViewById(R.id.blacklist);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        //this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        index.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomeActivity.class);
            ((Activity) context).finish();
            context.startActivity(intent);
            dismiss();
        });
        massage.setOnClickListener(view -> {
            Intent intent = new Intent(context, HomeActivity.class);
            intent.putExtra("position",3);
            ((Activity) context).finish();
            context.startActivity(intent);
            dismiss();
        });
        report.setOnClickListener(view -> {
//            ReportActivity.actionStart(context,id,type);
            dismiss();
        });
    }
    public void HideIndex(){
        index.setVisibility(View.GONE);
    }
    public void HideMassage(){
        massage.setVisibility(View.GONE);
    }
    public void HideShare(){
        share.setVisibility(View.GONE);
    }
    public void HideReport(){
        report.setVisibility(View.GONE);
    }
    public void HideDelete(){
        delete.setVisibility(View.GONE);
    }
    public void HideBlacklist(){
        blacklist.setVisibility(View.GONE);
    }

    public void setDelete(View.OnClickListener listener){
        delete.setOnClickListener(listener);
    }

    public void setBlacklist(View.OnClickListener listener){
        blacklist.setOnClickListener(listener);
    }

    public void shareClickListener(View.OnClickListener listener){
        share.setOnClickListener(listener);
    }

}
