package com.yuanyu.ceramics.broadcast.pull;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface LivePullConstract {
    interface ILivePullModel{
        Observable<BaseResponse<LivePullBean>> initData(String id);
    }
    interface ILivePullView{
        void initDataSuccess(LivePullBean bean);
        void showToast(String msg);
        void receiveMessageSuccess(LiveChatBean chatBean);
        void sentMassageSuccess(String msg);
        void initLivePull();
    }
    interface ILivePullPresenter{
        void initData(String id);
        void IMLogin(String useraccountid,String usersig,String groupId);
        void joinChatGroup(String groupid,String useraccountid);
        void quitChatGroup(String groupid);
        void sentMassage(String msg, TIMConversation conversation);
    }
}