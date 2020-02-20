package com.yuanyu.ceramics.bazaar;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StoreCenterPresenter extends BasePresenter<StoreCenterConstract.IStoreCenterView> implements StoreCenterConstract.IStoreCenterPresenter {
    private StoreCenterConstract.IStoreCenterModel model;
    StoreCenterPresenter() {model=new StoreCenterModel();}
    @Override
    public void getStoreCenterList(int page) {
        model.getStoreCenterList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<StoreCenterBean>>())
                .subscribe(new BaseObserver<List<StoreCenterBean>>() {
                    @Override
                    public void onNext(List<StoreCenterBean> storeCenterBeans) {
                        if(view!=null){view.getStoreCenterSuccess(storeCenterBeans);}
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getStoreCenterFail(e);}
                    }
                });
    }
}
