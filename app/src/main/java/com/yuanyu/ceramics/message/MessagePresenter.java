package com.yuanyu.ceramics.message;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MessagePresenter extends BasePresenter<MessageConstract.IMessageView> implements MessageConstract.IMessagePresenter {
    private MessageConstract.IMessageModel model;
    MessagePresenter() {model=new MessageModel();}

    @Override
    public void initData(String useraccountid) {
        model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MessageBean>>())
                .subscribe(new BaseObserver<List<MessageBean>>() {
                    @Override
                    public void onNext(List<MessageBean> beans) {
                        if(view!=null){view.initDataSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
