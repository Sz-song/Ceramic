package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyCoinsPresenter extends BasePresenter<MyCoinsConstract.IMyCoinsView> implements MyCoinsConstract.IMyCoinsPresenter {
    private MyCoinsConstract.IMyCoinsModel model;
    MyCoinsPresenter() {model=new MyCoinsModel();}
    @Override
    public void getMyCoinsDate(String useraccountid) {
        model.getMyCoinsDate(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        if(view!=null){view.getMyCoinsDateSuccess(s);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getMyCoinsDateFail(e);}
                    }
                });
    }
}
