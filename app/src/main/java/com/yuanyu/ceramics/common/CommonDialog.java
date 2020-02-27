package com.yuanyu.ceramics.common;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.yuanyu.ceramics.R;

public class CommonDialog extends Dialog implements View.OnClickListener {

    Context context;


    private TextView mTitle = null;

    private TextView mLeft;

    private TextView mRight;


    private String Content;// 内容

    private String leftBtnText;// 坐标按钮文字

    private String rightBtnText;// 右边按钮文字

    private float montentSize = 0;

    public float getContentSize() {
        return montentSize;
    }

    public void setContentSize(float contentSize) {
        this.montentSize = contentSize;
    }

    private DialogClickListener listener;

    public DialogClickListener getListener() {
        return listener;
    }

    /**
     * 设置点击按钮的监听事件
     *
     * @param listener
     */
    public void setListener(DialogClickListener listener) {
        this.listener = listener;
    }

    public String getContent() {
        return Content;
    }

    /**
     * 设置提示内容
     *
     * @param content
     */
    public void setContent(String content) {
        Content = content;
    }



    public String getLeftBtnText() {
        return leftBtnText;
    }

    /**
     * 设置左边按钮的文本
     *
     * @param leftBtnText
     */
    public void setLeftBtnText(String leftBtnText) {
        this.leftBtnText = leftBtnText;
    }


    public String getRightBtnText() {
        return rightBtnText;
    }

    /**
     * 设置右边按钮的文本
     *
     * @param rightBtnText
     */
    public void setRightBtnText(String rightBtnText) {
        this.rightBtnText = rightBtnText;
    }

    public CommonDialog(Context context) {
        super(context);
        this.context = context;
    }

    /**
     * 主题
     *
     * @param context
     * @param theme
     */
    public CommonDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    /**
     * 设置按钮的大小
     *
     * @param size
     */
    public void setBtnTextSize(int size) {
        mLeft.setTextSize(size);
        mRight.setTextSize(size);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_cancel);
        mTitle = findViewById(R.id.dialog_title);
        mLeft = findViewById(R.id.dialog_left);
        mRight = findViewById(R.id.dialog_right);
        mLeft.setOnClickListener(this);
        mRight.setOnClickListener(this);
        initView();
        initDialog(context);
    }

    /**
     * 设置dialog的宽为屏幕的3分之1
     *
     * @param context
     */
    private void initDialog(Context context) {
        setCanceledOnTouchOutside(false);
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
            }
        });
        WindowManager windowManager = this.getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = display.getWidth() / 6 * 5; // // 设置宽度
        this.getWindow().setAttributes(lp);
    }

    private void initView() {
        if (getContent() == null) {
            mTitle.setVisibility(View.GONE);
        } else {
            mTitle.setText(getContent());
            if (getContentSize() != 0)
                mTitle.setTextSize(getContentSize());
        }
        mLeft.setText(getLeftBtnText());
        mRight.setText(getRightBtnText());
    }

    public interface DialogClickListener {
        void onLeftBtnClick(Dialog dialog);

        void onRightBtnClick(Dialog dialog);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_left:
                listener.onLeftBtnClick(this);
                dismiss();
                break;
            case R.id.dialog_right:
                listener.onRightBtnClick(this);
                dismiss();
                break;
            default:
                break;
        }
    }
}
