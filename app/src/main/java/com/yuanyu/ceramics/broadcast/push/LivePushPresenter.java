package com.yuanyu.ceramics.broadcast.push;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

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
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.broadcast.LiveCustomMessage;
import com.yuanyu.ceramics.broadcast.pull.LiveChatBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LivePushPresenter extends BasePresenter<LivePushConstract.ILivePushView> implements LivePushConstract.ILivePushPresenter {
    private LivePushConstract.ILivePushModel model;
    LivePushPresenter() {model=new LivePushModel(); }

    @Override
    public void initData(String id) {
        model.initData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<LivePushBean>())
                .subscribe(new BaseObserver<LivePushBean>() {
                    @Override
                    public void onNext(LivePushBean bean) {if(view!=null){view.initDataSuccess(bean);}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.showToast("加载直播失败，请重试");} }
                });
    }

    @Override
    public void IMLogin(String useraccountid, String usersig,String nickname,String gronpID) {
        if(TIMManager.getInstance().getLoginUser()!=null&&TIMManager.getInstance().getLoginUser().equals(useraccountid)){
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
                if(view!=null){view.initLivePush();}
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
    public void sentMassage(String msgString, TIMConversation conversation, int type) {
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
        } else if(type==3){//商家切换商品
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
    public void getNumAudience(String groupId) {//获取直播间人数
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
    public void saveScreenshot(Bitmap bitmap, int type) {
        if (bitmap != null) {
            try {// 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + System.currentTimeMillis()+".JPEG";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                Uri uri = Uri.fromFile(file);
                if(view!=null){view.saveScreenshotSuccess(uri,type,filePath);}
            } catch (Exception e) {
                L.e(e.getMessage());
                if(view!=null){view.saveScreenshotFail(type);}
            }
        }else{
            if(view!=null){view.saveScreenshotFail(type);}
        }
    }

    @Override
    public void setItemPosition(String groupId,String item_id) {
        TIMGroupManager.ModifyGroupInfoParam param = new TIMGroupManager.ModifyGroupInfoParam(groupId);
        Map<String, byte[]> customInfo = new HashMap<>();
        try {
            customInfo.put("item_id", item_id.getBytes("utf-8"));
            param.setCustomInfo(customInfo);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TIMGroupManager.getInstance().modifyGroupInfo(param, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                L.e("modify group info failed, code:" + code +"|desc:" + desc);
                if(view!=null){view.showToast("商品展示设置失败");}
            }
            @Override
            public void onSuccess() {
                if(view!=null){view.setItemPositionSuccess(item_id);}
            }
        });
    }

    @Override
    public void finishLive(String live_id) {
        model.finishLive(live_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){view.showToast("直播结束");}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { L.e(e.message+"  "+e.status); }
                });
    }
}
