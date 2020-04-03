package com.yuanyu.ceramics.chat;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChatPresenter extends BasePresenter<ChatConstract.IChatView> implements ChatConstract.IChatPresenter {
    private ChatConstract.IChatModel model;
    ChatPresenter() { model=new ChatModel(); }
    @Override
    public void getChaterInfo(String useraccountid) {
        model.getChaterInfo(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<ChatBean>())
                .subscribe(new BaseObserver<ChatBean>() {
                    @Override
                    public void onNext(ChatBean bean) {
                        if(view!=null){view.getChaterInfoSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.showToast(e.message);}
                    }
                });
    }

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


    @Override
    public void ConversationInit(TIMConversation conversation,String useraccountid) {//消息监听器
        conversation.getMessage(10000, conversation.getLastMsg(), new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                L.e("code"+i+" "+s);
                if(view!=null){view.showToast("获取历史消息失败:"+s);}
            }

            @Override
            public void onSuccess(List<TIMMessage> timMessages) {
                List<TIMMessage> listmsg=new ArrayList<>();
                for(int i=timMessages.size();i>0;i--){
                    listmsg.add(timMessages.get(i-1));
                }
                listmsg.add(conversation.getLastMsg());
                for (int i = 0; i < listmsg.size(); i++) {
                    TIMMessage msg = listmsg.get(i);
                    String senter =msg.getSender();
                    for (int j = 0; j < msg.getElementCount(); ++j) {
                        TIMElem elem = msg.getElement(j);
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        if (elemType == TIMElemType.Text) {
                            TIMTextElem textElem = (TIMTextElem) elem;
                            if(senter.equals(useraccountid)){
                                if(view!=null){view.receiveMessageSuccess(textElem.getText(),true);}
                            }else{
                                if(view!=null){view.receiveMessageSuccess(textElem.getText(),false);}
                            }
                        } else if (elemType == TIMElemType.Image) {
                            //处理图片消息
                        }
                    }
                }
            }
        });
        TIMManager.getInstance().addMessageListener(TIMMessagelist -> {//收到新消息
            for (int i = 0; i < TIMMessagelist.size(); i++) {
                if (TIMMessagelist.get(i).getConversation().getPeer().equals(conversation.getPeer())) {
                    TIMMessage msg = TIMMessagelist.get(i);
                    String senter =msg.getSender();
                    for (int j = 0; j < msg.getElementCount(); ++j) {
                        TIMElem elem = msg.getElement(j);
                        //获取当前元素的类型
                        TIMElemType elemType = elem.getType();
                        if (elemType == TIMElemType.Text) {
                            TIMTextElem textElem = (TIMTextElem) elem;
                            if(senter.equals(useraccountid)){
                                if(view!=null){view.receiveMessageSuccess(textElem.getText(),true);}
                            }else{
                                if(view!=null){view.receiveMessageSuccess(textElem.getText(),false);}
                            }
                        } else if (elemType == TIMElemType.Image) {
                            //处理图片消息
                        }
                    }
                }
            }
            return false;
        });
    }
}
