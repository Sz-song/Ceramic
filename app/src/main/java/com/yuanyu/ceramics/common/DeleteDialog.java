package com.yuanyu.ceramics.common;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.yuanyu.ceramics.R;


public class DeleteDialog extends AlertDialog {
    private TextView title;
    private Button no;
    private Button yes;
    private String titleStr;//从外界设置的title文本

    private String yesStr, noStr;//确定文本和取消文本的显示内容

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器

    public DeleteDialog(@NonNull Context context) {
        super(context, R.style.DeleteDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_dialog);
        yes = findViewById(R.id.yes);
        no = findViewById(R.id.no);
        title = findViewById(R.id.title);
        setCanceledOnTouchOutside(false);
        initData();
        initEvent();
    }

    public void setTitle(String title) {
        this.titleStr = title;
    }

    public void setYesStr(String yesStr) {
        this.yesStr = yesStr;
    }

    public void setNoStr(String noStr) {
        this.noStr = noStr;
    }

    public void setNoOnclickListener(onNoOnclickListener onNoOnclickListener) {
        this.noOnclickListener = onNoOnclickListener;
    }

    public void setYesOnclickListener(onYesOnclickListener onYesOnclickListener) {
        this.yesOnclickListener = onYesOnclickListener;
    }

    public interface onYesOnclickListener {
        void onYesClick();
    }

    public interface onNoOnclickListener {
        void onNoClick();
    }
    private void initData() {
        //如果用户自定了title和message
        if (titleStr != null) {
            title.setText(titleStr);
        }
        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }
        if (noStr != null) {
            no.setText(noStr);
        }
    }
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(v -> {
            if (yesOnclickListener != null) {
                yesOnclickListener.onYesClick();
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(v -> {
            if (noOnclickListener != null) {
                noOnclickListener.onNoClick();
            }
        });
    }


}
