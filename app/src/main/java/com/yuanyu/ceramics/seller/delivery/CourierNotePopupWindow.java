package com.yuanyu.ceramics.seller.delivery;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnStringCallback;

import java.util.List;

public class CourierNotePopupWindow extends PopupWindow {

    private View view;
    private TextView note0,note1,note2,note3 ,textNum,commit;
    private EditText noteTxt;
    private Context context;
    private String note_txt;
    private List<Boolean> list;
    private OnStringCallback onStringCallback;

    public void setOnStringCallback(OnStringCallback onStringCallback) {
        this.onStringCallback = onStringCallback;
    }

    public CourierNotePopupWindow(Context context, String note_txt, List<Boolean> list) {
        super(context);
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_couriernote, null);
        this.note0=view.findViewById(R.id.note0);
        this.note1=view.findViewById(R.id.note1);
        this. note2=view.findViewById(R.id.note2);
        this.note3=view.findViewById(R.id.note3);
        this. noteTxt=view.findViewById(R.id.note_txt);
        this.textNum=view.findViewById(R.id.text_num);
        this.commit=view.findViewById(R.id.commit);
        this.context=context;
        this.note_txt= note_txt;
        this.list=list;
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
            onStringCallback.callback(noteTxt.getText().toString());
            WindowManager.LayoutParams lp1 = ((Activity)context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        if(list.get(0)){
            note0.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
            note0.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            note0.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
            note0.setTextColor(context.getResources().getColor(R.color.lightGray));
        }
        if(list.get(1)){
            note1.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
            note1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            note1.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
            note1.setTextColor(context.getResources().getColor(R.color.lightGray));
        }
        if(list.get(2)){
            note2.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
            note2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            note2.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
            note2.setTextColor(context.getResources().getColor(R.color.lightGray));
        }
        if(list.get(3)){
            note3.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
            note3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }else{
            note3.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
            note3.setTextColor(context.getResources().getColor(R.color.lightGray));
        }
        noteTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                textNum.setText((30-editable.toString().length())+"");
            }
        });
        noteTxt.setText(note_txt);
        commit.setOnClickListener(view -> {
            dismiss();
        });
        note0.setOnClickListener(view -> {
            if(list.get(0)){
                list.set(0,false);
                note0.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
                note0.setTextColor(context.getResources().getColor(R.color.lightGray));
            }else{
                list.set(0,true);
                note0.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
                note0.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });
        note1.setOnClickListener(view -> {
            if(list.get(1)){
                list.set(1,false);
                note1.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
                note1.setTextColor(context.getResources().getColor(R.color.lightGray));
            }else{
                list.set(1,true);
                note1.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
                note1.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });
        note2.setOnClickListener(view -> {
            if(list.get(2)){
                list.set(2,false);
                note2.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
                note2.setTextColor(context.getResources().getColor(R.color.lightGray));
            }else{
                list.set(2,true);
                note2.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
                note2.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });
        note3.setOnClickListener(view -> {
            if(list.get(3)){
                list.set(3,false);
                note3.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stlightgray));
                note3.setTextColor(context.getResources().getColor(R.color.lightGray));
            }else{
                list.set(3,true);
                note3.setBackground(context.getResources().getDrawable(R.drawable.r2_sowhite_stpri));
                note3.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        });

    }
}

