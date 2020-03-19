package com.yuanyu.ceramics.chat;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.yuanyu.ceramics.base.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

public interface ChatConstract {
    interface IChatModel {
        Observable<BaseResponse<ChatBean>> getChaterInfo(String useraccountid);
    }

    interface IChatView {
        void getChaterInfoSuccess(ChatBean bean);
        void sentMassageSuccess(String msg);
        void showToast(String msg);
        void receiveMessageSuccess(List<TIMMessage> TIMMessagelist);
    }

    interface IChatPresenter {
        void getChaterInfo(String useraccountid);
        void sentMassage(String msg, TIMConversation conversation);
        void ConversationInit(TIMConversation conversation);
    }
}
