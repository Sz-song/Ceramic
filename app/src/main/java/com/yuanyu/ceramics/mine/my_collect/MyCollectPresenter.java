package com.yuanyu.ceramics.mine.my_collect;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCollectPresenter extends BasePresenter<MyCollectConstract.IMyCollectView> implements MyCollectConstract.IMyCollectPresenter {
    private MyCollectConstract.IMyCollectModel model;
    MyCollectPresenter() { model=new MyCollectModel();}

    @Override
    public void getMyCollect(String useraccountid,int page) {
        model.getMyCollect(useraccountid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<MyCollectBean>>())
                .subscribe(new BaseObserver<List<MyCollectBean>>() {
                    @Override
                    public void onNext(List<MyCollectBean> beans) {
                        if(view!=null){view.getMyCollectSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getMyCollectFail(e); }
                    }
                });
    }
}
