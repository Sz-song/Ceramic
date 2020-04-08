package com.yuanyu.ceramics.broadcast.push;

import android.graphics.Bitmap;
import android.net.Uri;

import com.tencent.imsdk.TIMConversation;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.broadcast.pull.LiveChatBean;

import io.reactivex.Observable;

public interface LivePushConstract {
    interface ILivePushModel{
        Observable<BaseResponse<LivePushBean>> initData(String id);
        Observable<BaseResponse<String[]>> finishLive(String id);
    }
    interface ILivePushView{
        void initDataSuccess(LivePushBean bean);
        void initLivePush();
        void receiveMessageSuccess(LiveChatBean chatBean);
        void showToast(String msg);
        void switchFilter(int position);//切换滤镜
        void switchSharpness(int postion);//切换清晰度
        void getNumAudienceSuccess(int num);
        void sentMassageSuccess(String msg,int type);
        void saveScreenshotSuccess(Uri uri, int type, String filePath);
        void saveScreenshotFail(int type);
        void setItemPositionSuccess(String item_id);
    }
    interface ILivePushPresenter{
        void initData(String id);
        void IMLogin(String useraccountid,String usersig,String nickname,String groupId);
        void joinChatGroup(String groupid,String useraccountid,String nickname);
        void quitChatGroup(String groupid);
        void sentMassage(String msg, TIMConversation conversation, int type);
        void getNumAudience(String groupId);
        void saveScreenshot(Bitmap bitmap,int type);
        void setItemPosition(String groupId, String item_id);
        void finishLive(String live_id);
    }
}
