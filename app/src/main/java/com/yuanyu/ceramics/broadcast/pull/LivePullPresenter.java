package com.yuanyu.ceramics.broadcast.pull;

import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfo;
import com.tencent.imsdk.ext.group.TIMGroupDetailInfoResult;
import com.tencent.openqq.protocol.imsdk.msg;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.broadcast.LiveCustomMessage;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LivePullPresenter extends BasePresenter<LivePullConstract.ILivePullView> implements LivePullConstract.ILivePullPresenter {
    private LivePullConstract.ILivePullModel model;

    LivePullPresenter() { model=new LivePullModel();}

    @Override
    public void initData(String id) {
        model.initData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<LivePullBean>())
                .subscribe(new BaseObserver<LivePullBean>() {
                    @Override
                    public void onNext(LivePullBean bean) {if(view!=null){view.initDataSuccess(bean);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.showToast("加载直播失败，请重试");} }
                });
    }

    @Override
    public void IMLogin(String useraccountid, String usersig,String nickname,String gronpID) {
        if(TIMManager.getInstance().getLoginUser().equals(useraccountid)){
            if(view!=null){joinChatGroup(gronpID,nickname,useraccountid);}
        }else{
            TIMManager.getInstance().login(useraccountid, usersig, new TIMCallBack() {
                @Override
                public void onError(int code, String desc) {
                    L.d("login failed. code: " + code + " errmsg: " + desc);
                    if(view!=null){view.showToast("数据加载失败您可能无法正常观看直播");}
                }
                @Override
                public void onSuccess() {
                    if(view!=null){joinChatGroup(gronpID,nickname,useraccountid);}
                }
            });
        }
    }

    @Override
    public void joinChatGroup(String grounpid,String nickname,String useraccountid) {
//      设置新消息监听
        TIMManager.getInstance().addMessageListener(TIMMessagelist -> {//收到新消息
            for (int i = 0; i < TIMMessagelist.size(); i++) {
                if (TIMMessagelist.get(i).getConversation().getPeer().equals(grounpid)) {
                    TIMMessage msg = TIMMessagelist.get(i);
                    msg.getSenderProfile(new TIMValueCallBack<TIMUserProfile>() {
                        @Override
                        public void onError(int i, String s) {
                            L.e("资料获取失败");
                        }

                        @Override
                        public void onSuccess(TIMUserProfile timUserProfile) {
                            for (int j = 0; j < msg.getElementCount(); ++j) {
                                TIMElem elem = msg.getElement(j);
                                //获取当前元素的类型
                                TIMElemType elemType = elem.getType();
                                if (elemType == TIMElemType.Text) {
                                    TIMTextElem textElem = (TIMTextElem) elem;
                                    LiveChatBean chatBean = new LiveChatBean(timUserProfile.getIdentifier(), timUserProfile.getNickName(), textElem.getText(),0);
                                    if(view!=null){
                                        view.receiveMessageSuccess(chatBean);
                                    }
                               }else if(elemType == TIMElemType.Custom){
                                    TIMCustomElem customElem = (TIMCustomElem) elem;
                                    Gson gson=new Gson();
                                    L.e(new String(customElem.getData()));
                                    LiveCustomMessage customMessage=gson.fromJson(new String(customElem.getData()),LiveCustomMessage.class);
                                    LiveChatBean chatBean = new LiveChatBean(timUserProfile.getIdentifier(), timUserProfile.getNickName(), customMessage.getMsg(),customMessage.getType());
                                    if(view!=null){
                                        view.receiveMessageSuccess(chatBean);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            return false;
        });
        List<String> user = new ArrayList<>();
        user.add(useraccountid);
        TIMGroupManager.getInstance().applyJoinGroup(grounpid, "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) { L.e("加入聊天室失败: "+s);}
            @Override
            public void onSuccess() {
                TIMGroupManager.ModifyMemberInfoParam param = new TIMGroupManager.ModifyMemberInfoParam(grounpid, useraccountid);
                param.setNameCard(nickname);
                TIMGroupManager.getInstance().modifyMemberInfo(param, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        L.e("modifyMemberInfo failed, code:" + code + "|msg: " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        L.e("modifyMemberInfo succ");
                    }
                });
                if(view!=null){view.initLivePull();}
            }
        });
    }

    @Override
    public void quitChatGroup(String groupid) {
        TIMGroupManager.getInstance().quitGroup(groupid, new TIMCallBack() {
            @Override
            public void onError(int i, String s) { L.e(s);}
            @Override
            public void onSuccess() {}
        });
    }



    @Override
    public void sentMassage(String msgString, TIMConversation conversation,int type) {
        TIMMessage msg = new TIMMessage();//构造一条消息
        if(type==0){//发送聊天信息
            TIMTextElem elem_msg = new TIMTextElem();//添加文本内容
            elem_msg.setText(msgString);
            if(msg.addElement(elem_msg) != 0) {//将elem添加到消息
                if(view!=null){view.showToast("消息发送失败");}
                return;
            }
        }else if(type==1){
            TIMCustomElem elem_custom = new TIMCustomElem();
            elem_custom.setData(msgString.getBytes());//自定义 byte[]
            if(msg.addElement(elem_custom) != 0) {
                L.e("进场消息失败");
                return;
            }
        }else if(type==2){
            TIMCustomElem elem_custom = new TIMCustomElem();
            elem_custom.setData(msgString.getBytes());//自定义 byte[]
            if(msg.addElement(elem_custom) != 0) {
                L.e("退场消息失败");
                return;
            }
        }
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                L.e("send message failed. code: " + code + " errmsg: " + desc);
                if(view!=null){view.showToast("消息发送失败");}
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                L.e( "SendMsg ok");
                if(view!=null){view.sentMassageSuccess(msgString,type);}
            }
        });
    }

    @Override
    public void  getNumAudience(String groupId) {//获取直播间人数
        TIMGroupManager.getInstance().getGroupMembers(groupId, new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
            @Override
            public void onError(int i, String s) {
                if(view!=null){getNumAudience(groupId);}
            }

            @Override
            public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                if(view!=null){
                    view.getNumAudienceSuccess(timGroupMemberInfos.size());
                }
            }
        });
    }

    @Override
    public void getItemPosition(String groupId) {
        ArrayList<String> groupList = new ArrayList<>();
        groupList.add(groupId);
        TIMGroupManager.getInstance().getGroupInfo(groupList, new TIMValueCallBack<List<TIMGroupDetailInfoResult>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMGroupDetailInfoResult> timGroupDetailInfoResults) {
                for(int i=0;i<timGroupDetailInfoResults.size();i++){
                    if(timGroupDetailInfoResults.get(i).getGroupId().equals(groupId)){
                        if(timGroupDetailInfoResults.get(i).getCustom().get("item_id")!=null&&view!=null){
                            view.changeItem(new String(timGroupDetailInfoResults.get(i).getCustom().get("item_id")));
                        }
                        L.e("item_id is"+new String(timGroupDetailInfoResults.get(i).getCustom().get("item_id")));
                    }
                }
            }
        });
    }
}
