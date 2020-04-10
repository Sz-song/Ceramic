package com.yuanyu.ceramics.seller.message_shop;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.utils.L;

import java.util.ArrayList;
import java.util.List;

public class MessageShopPresenter extends BasePresenter<MessageShopConstract.IMessageShopView> implements MessageShopConstract.IMessageShopPresenter {
    private MessageShopConstract.IMessageShopModel model;

    MessageShopPresenter() {
        model= new MessageShopModel();
    }

    @Override
    public void initData() {
        List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
        TIMManager.getInstance().addMessageListener(TIMMessagelist -> {//收到新消息监听
            for (int i = 0; i < TIMMessagelist.size(); i++) {
                TIMMessage msg = TIMMessagelist.get(i);
                String senter =msg.getSender();
                for (int j = 0; j < msg.getElementCount(); ++j) {
                    TIMElem elem = msg.getElement(j);
                    //获取当前元素的类型
                    TIMElemType elemType = elem.getType();
                    if (elemType == TIMElemType.Text) {
                        TIMTextElem textElem = (TIMTextElem) elem;
                        if(view!=null){view.receiveMessageSuccess(textElem.getText(),senter);}
                    } else if (elemType == TIMElemType.Image) {
                        //处理图片消息
                    }

                }
            }
            return false;
        });

        List<String> list=new ArrayList<>();
        for(int i=0;i<conversationList.size();i++){
            list.add(conversationList.get(i).getPeer());
        }
        List<MessageBean> beans =new ArrayList<>();
        for(int i=0;i<list.size();i++){
            //获取最后一条消息
            MessageBean bean=new MessageBean();
            bean.setUseraccountid(list.get(i));
            if(conversationList.get(i).getLastMsg().getElement(0).getType()== TIMElemType.Text){
                TIMTextElem textElem =(TIMTextElem)conversationList.get(i).getLastMsg().getElement(0);
                bean.setLastMsg(textElem.getText());
            }
            bean.setTime(conversationList.get(i).getLastMsg().timestamp());
            bean.setUnreadnum(conversationList.get(i).getUnreadMessageNum());
            beans.add(bean);
        }
        TIMFriendshipManager.getInstance().getUsersProfile(list, true, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
                L.e("获取对话失败"+i+s);
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                for(int i=0;i<beans.size();i++){
                    for(int j=0;j<timUserProfiles.size();j++){
                        if(beans.get(i).getUseraccountid().equals(timUserProfiles.get(j).getIdentifier())){
                            beans.get(i).setIcon(timUserProfiles.get(j).getFaceUrl());
                            beans.get(i).setNickname(timUserProfiles.get(j).getNickName());
                        }
                    }
                }
                if(view!=null){view.initDataSuccess(beans);}
            }
        });
    }
}
