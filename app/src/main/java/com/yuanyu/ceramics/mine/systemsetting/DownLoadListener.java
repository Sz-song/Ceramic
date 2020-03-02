package com.yuanyu.ceramics.mine.systemsetting;

public interface DownLoadListener {
    void onProgress(int progress);
    void onSuccess();
    void onFailed();
    void onPaused();
    void onCancle();
}
