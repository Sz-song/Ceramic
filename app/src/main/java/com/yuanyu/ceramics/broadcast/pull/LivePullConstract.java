package com.yuanyu.ceramics.broadcast.pull;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;

import java.util.List;

public interface LivePullConstract {
    interface ILivePullModel{

    }
    interface ILivePullView{
        void initLivePull();
        void showToast(String msg);
        void receiveMessageSuccess(List<TIMMessage> TIMMessagelist);
        void sentMassageSuccess(String msg);
    }
    interface ILivePullPresenter{
        void initLivePull();
        void sentMassage(String msg, TIMConversation conversation);
    }
}
