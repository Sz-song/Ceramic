package com.yuanyu.ceramics.utils;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;

public class ShareUiListener implements IUiListener {

    @Override
    public void onComplete(Object response) {
        //分享成功
        L.e(response.toString());
    }

    @Override
    public void onError(UiError uiError) {
        //分享失败

    }

    @Override
    public void onCancel() {
        //分享取消
    }
}
