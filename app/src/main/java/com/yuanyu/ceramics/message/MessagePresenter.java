package com.yuanyu.ceramics.message;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMTextElem;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageConstract.IMessageView> implements MessageConstract.IMessagePresenter {
    private MessageConstract.IMessageModel model;
    MessagePresenter() {model=new MessageModel();}

    @Override
    public void initData() {
        List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
        TIMManager.getInstance().addMessageListener(TIMMessagelist -> {//收到新消息监听
            if(view!=null){view.receiveMessageSuccess();}
            return false;
        });
        List<String> list=new ArrayList<>();
        for(int i=0;i<conversationList.size();i++){
            list.add(conversationList.get(i).getPeer());
        }
        model.initData(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MessageBean>>())
                .subscribe(new BaseObserver<List<MessageBean>>() {
                    @Override
                    public void onNext(List<MessageBean> beans) {
                        for(int i=0;i<beans.size();i++){
//                          获取最后一条消息
                            if(conversationList.get(i).getLastMsg().getElement(0).getType()== TIMElemType.Text){
                                TIMTextElem textElem =(TIMTextElem)conversationList.get(i).getLastMsg().getElement(0);
                                beans.get(i).setLastMsg(textElem.getText());
                            }
                            beans.get(i).setTime(conversationList.get(i).getLastMsg().timestamp());
                            beans.get(i).setUnreadnum(conversationList.get(i).getUnreadMessageNum());

                        }
                        if(view!=null){view.initDataSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
