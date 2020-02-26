package com.yuanyu.ceramics.utils;

import android.content.Context;
import android.widget.Toast;

import com.yuanyu.ceramics.MyApplication;

public class ToastUtils {
    private static Toast toast;
    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}