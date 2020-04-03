package com.yuanyu.ceramics.broadcast.push;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.broadcast.pull.LiveChatBean;

import io.reactivex.Observable;

public interface LivePushConstract {
    interface ILivePushModel{
        Observable<BaseResponse<LivePushBean>> initData(String id);
    }
    interface ILivePushView{
        void initDataSuccess(LivePushBean bean);
        void initLivePush();
        void receiveMessageSuccess(LiveChatBean chatBean);
        void showToast(String msg);
        void switchFilter(int position);//切换滤镜
        void switchSharpness(int postion);//切换清晰度
    }
    interface ILivePushPresenter{
        void initData(String id);
        void IMLogin(String useraccountid,String usersig,String nickname,String groupId);
        void joinChatGroup(String groupid,String useraccountid,String nickname);
        void quitChatGroup(String groupid);
    }
}
