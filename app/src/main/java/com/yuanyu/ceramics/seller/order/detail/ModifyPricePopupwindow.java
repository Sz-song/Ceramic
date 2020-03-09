package com.yuanyu.ceramics.seller.order.detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnStringCallback;


public class ModifyPricePopupwindow extends PopupWindow {
    private Context context;
    private View view;
    private TextView cancel,submit;
    private EditText new_price;
    private OnStringCallback onModityListener;

    public void setOnModityListener(OnStringCallback onModityListener) {
        this.onModityListener = onModityListener;
    }

    @SuppressLint("WrongConstant")
    ModifyPricePopupwindow(Context context, String price) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_modify_price, null);
        this.setContentView(this.view);
        cancel=view.findViewById(R.id.cancel);
        submit=view.findViewById(R.id.submit);
        new_price=view.findViewById(R.id.new_price);
        new_price.setText(price);
        this.setOutsideTouchable(true);//外部点击消失
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        this.cancel.setOnClickListener(v -> dismiss());
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
            InputMethodManager inputmanger = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(new_price.getWindowToken(), 0);
        });
        new_price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //删除“.”后面超过2位后的数据
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2+1);
                        new_price.setText(s);
                        new_price.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    new_price.setText(s);
                    new_price.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0") &&s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        new_price.setText(s.subSequence(0, 1));
                        new_price.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(v -> {
            try {
                if(new_price.getText().toString().trim().length()==0){
                    Toast.makeText(context, "请输入价格", Toast.LENGTH_SHORT).show();
                }else if(Double.parseDouble(new_price.getText().toString())==0){
                    Toast.makeText(context, "价格不能低于0", Toast.LENGTH_SHORT).show();
                }else{
                    onModityListener.callback(new_price.getText().toString());
                }
            }catch (Exception e){
                Toast.makeText(context, "请输入正确价格", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void show(View parent, int gravity, int x, int y) {
        this.showAtLocation(parent,gravity,x,y);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        new_price.setSelectAllOnFocus(true);
        showSoft();
    }

    private void showSoft(){
        Handler handle=new Handler();
        handle.postDelayed(() -> {
            InputMethodManager inputMethodManager=(InputMethodManager) new_price.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(new_price, 0);
        }, 0);
    }
}
