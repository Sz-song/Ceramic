package com.yuanyu.ceramics.chat;

import com.tencent.imsdk.TIMConversation;

public interface ChatConstract {
    interface IChatModel {

    }

    interface IChatView {
        void sentMassageSuccess(String msg);
        void showToast(String msg);
    }

    interface IChatPresenter {
        void sentMassage(String msg, TIMConversation conversation);
    }
}
