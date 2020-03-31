package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;

class LivePopupwindowSkinCare extends PopupWindow {
    private Context context;
    private View view;
    private SeekBar seekBar;
    private OnPositionClickListener onPositionClickListener;

    void setOnPositionClickListener(OnPositionClickListener onPositionClickListener) {
        this.onPositionClickListener = onPositionClickListener;
    }

    LivePopupwindowSkinCare(Context context) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_live_skincare, null);
        this.seekBar=view.findViewById(R.id.seek_bar);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(seekBar.getProgress()==100){
                    onPositionClickListener.callback(9);
                }else{
                    onPositionClickListener.callback(seekBar.getProgress()/10);
                }

            }
        });
    }
}
