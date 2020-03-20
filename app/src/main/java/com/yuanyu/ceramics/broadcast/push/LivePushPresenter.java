package com.yuanyu.ceramics.broadcast.push;

import com.yuanyu.ceramics.base.BasePresenter;

public class LivePushPresenter extends BasePresenter<LivePushConstract.ILivePushView> implements LivePushConstract.ILivePushPresenter {
    private LivePushConstract.ILivePushModel model;
    LivePushPresenter() {model=new LivePushModel(); }
}
