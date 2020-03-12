package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SellerIndexPresenter extends BasePresenter<SellerIndexConstract.IMineView> implements SellerIndexConstract.IMinePresenter {
    private SellerIndexConstract.IMineModel model;
    SellerIndexPresenter() {model=new SellerIndexModel();}
    @Override
    public void initData(String useraccountid) {
                model.initData(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<SellerIndexBean>())
                .subscribe(new BaseObserver<SellerIndexBean>() {
                    @Override
                    public void onNext(SellerIndexBean bean) {
                        if(view!=null){view.initDataSuccess(bean);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.initDataFail(e);}
                    }
                });
    }
}
