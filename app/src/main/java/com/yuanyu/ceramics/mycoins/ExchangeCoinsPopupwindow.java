package com.yuanyu.ceramics.mycoins;

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


public class ExchangeCoinsPopupwindow extends PopupWindow {
    private EditText trueName,alipayNum;
    private TextView cancel, submit;
    private CheckExchangeListener exchangeListener;
    private Context context;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setExchangeListener(CheckExchangeListener exchangeListener) {
        this.exchangeListener = exchangeListener;
    }

    public ExchangeCoinsPopupwindow(Context context) {
        super(context);
        this.context=context;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_exchange_coins, null);
        trueName=view.findViewById(R.id.true_name);
        alipayNum=view.findViewById(R.id.alipay_num);
        cancel=view.findViewById(R.id.cancel);
        submit =view.findViewById(R.id.submit);
        this.setContentView(view);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        cancel.setOnClickListener(v -> dismiss());
        submit.setOnClickListener(v -> {
            if(trueName.getText().toString().length()==0){
                Toast.makeText(context, "请填写真实姓名", Toast.LENGTH_SHORT).show();
                return;
            }
            if(alipayNum.getText().toString().length()==0){
                Toast.makeText(context, "请填写支付宝账号", Toast.LENGTH_SHORT).show();
                return;
            }
            exchangeListener.CheckChange(trueName.getText().toString(),alipayNum.getText().toString(),id);
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }

    public  interface CheckExchangeListener{
        void CheckChange(String true_name, String alipay_num, String id);
    }
}
