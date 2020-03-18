package com.yuanyu.ceramics.chat;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.L;

public class ChatPresenter extends BasePresenter<ChatConstract.IChatView> implements ChatConstract.IChatPresenter {

    @Override
    public void sentMassage(String msgString, TIMConversation conversation) {
        TIMMessage msg = new TIMMessage();//构造一条消息
        TIMTextElem elem = new TIMTextElem();//添加文本内容
        elem.setText(msgString);
        if(msg.addElement(elem) != 0) {//将elem添加到消息
            if(view!=null){view.showToast("消息发送失败");}
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                L.e("send message failed. code: " + code + " errmsg: " + desc);
                if(view!=null){view.showToast("消息发送失败");}
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                L.e( "SendMsg ok");
                if(view!=null){view.sentMassageSuccess(msgString);}
            }
        });
    }
}
