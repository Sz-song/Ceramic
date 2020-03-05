package com.yuanyu.ceramics.common;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.yuanyu.ceramics.R;


public class CommonDialog2 extends AlertDialog {
    private TextView title;
    private Button no;
    private Button yes;
    private String titleStr;//从外界设置的title文本
    private String yesStr, noStr;//确定文本和取消文本的显示内容
    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public CommonDialog2(@NonNull Context context, String titleStr, String yesStr, String noStr) {
        super(context, R.style.DeleteDialog);
        this.titleStr=titleStr;
        this.yesStr=yesStr;
        this.noStr=noStr;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_dialog);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        title = findViewById(R.id.title);
        setCanceledOnTouchOutside(false);
        initEvent();
    }
    private void initEvent() {
        title.setText(titleStr);
        yes.setText(yesStr);
        no.setText(noStr);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yesOnclickListener.onYesClick();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noOnclickListener.onNoClick();
            }
        });
    }
    public void setYesOnclick(onYesOnclickListener yesOnclickListener){
        this.yesOnclickListener=yesOnclickListener;
    }
    public void setNoOnclick(onNoOnclickListener noOnclickListener){
        this.noOnclickListener=noOnclickListener;
    }
    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }
}
