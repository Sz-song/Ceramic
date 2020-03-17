package com.yuanyu.ceramics.message;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMManager;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageConstract.IMessageView> implements MessageConstract.IMessagePresenter {
    private MessageConstract.IMessageModel model;
    MessagePresenter() {model=new MessageModel();}

    @Override
    public void initData() {
        List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
        List<String> list=new ArrayList<>();
        list.add("1355");
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
                            beans.get(i).setLastMsg("你好");
                            beans.get(i).setTime(new Date().getTime());
//                            beans.get(i).setLastMsg(conversationList.get(i).getLastMsg().getCustomStr());
//                            beans.get(i).setLastMsg(TimeUtils.CountTime(conversationList.get(i).getLastMsg().timestamp()));
//                            beans.get(i).setUnreadnum(conversationList.get(i).getUnreadMessageNum());
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
