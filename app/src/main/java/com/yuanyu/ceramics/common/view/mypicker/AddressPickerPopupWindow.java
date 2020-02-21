package com.yuanyu.ceramics.common.view.mypicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yuanyu.ceramics.R;


public class AddressPickerPopupWindow extends PopupWindow {
    private View view;
    private AddressPickerView addressPickerView;
    public AddressPickerPopupWindow(Context context) {
        super(context);
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_address_picker, null);
        addressPickerView=view.findViewById(R.id.apvaddress);
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
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = ((Activity)context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
    }
    public void setAddressPickerSure(AddressPickerView.OnAddressPickerSureListener listener){
        addressPickerView.setOnAddressPickerSure(listener);
    }
}
