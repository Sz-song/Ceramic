package com.yuanyu.ceramics.dingzhi;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;

public class SelectPopupWindow extends PopupWindow {
    private RecyclerView recyclerview;
    private Button reset;
    private Button confirm;
    private TextView num;
    private Context mContext;
    private View view;

    public SelectPopupWindow(Context context) {
        mContext = context;
        this.view = LayoutInflater.from(mContext).inflate(R.layout.popup_layout, null);
        recyclerview = view.findViewById(R.id.popup_recyclerview);
        reset = view.findViewById(R.id.popup_reset);
        confirm = view.findViewById(R.id.popup_confirm);
        num = view.findViewById(R.id.num);
        this.setOutsideTouchable(true);//外部点击消失
//        this.view.setOnTouchListener((view, motionEvent) -> {
//            int height = view.findViewById(R.id.popup_layout).getTop();
//            int y = (int)motionEvent.getY();
//            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
//                if (y < height){dismiss();}
//            }
//            return true;
//        });
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x000000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
    }
    public void setAdapter(FenleiTypeAdapter adapter){
        GridLayoutManager lm = new GridLayoutManager(mContext,4);
        recyclerview.setLayoutManager(lm);
        recyclerview.setAdapter(adapter);
    }

    public void reset(View.OnClickListener listener){
        reset.setOnClickListener(listener);
    }
    public void confirm(View.OnClickListener listener){
        confirm.setOnClickListener(listener);
    }

}
