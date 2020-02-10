package com.yuanyu.ceramics.broadcast;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BroadcastFragmentPresenter extends BasePresenter<BroadcastFragmentConstract.IBroadcastFragmentView> implements BroadcastFragmentConstract.IBroadcastFragmentPresenter {
    private BroadcastFragmentConstract.IBroadcastFragmentModel model;
    BroadcastFragmentPresenter() {model=new BroadcastFragmentModel();}

    @Override
    public void initData(String useraccountid) {
        model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<BroadcastBean>>())
                .subscribe(new BaseObserver<List<BroadcastBean>>() {
                    @Override
                    public void onNext(List<BroadcastBean> beans) {
                        if(view!=null){view.initDataSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
