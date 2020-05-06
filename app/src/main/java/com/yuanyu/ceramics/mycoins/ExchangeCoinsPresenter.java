package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ExchangeCoinsPresenter extends BasePresenter<ExchangeCoinsConstract.IExchangeCoinsView> implements ExchangeCoinsConstract.IExchangeCoinsPresenter {
    private ExchangeCoinsConstract.IExchangeCoinsModel model;

    ExchangeCoinsPresenter() {
        model=new ExchangeCoinsModel();
    }

    @Override
    public void getMyCoinsExchange(String useraccountid) {
        model.getMyCoinsExchange(useraccountid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<ExchangeCoinsBean>>())
                .subscribe(new BaseObserver<List<ExchangeCoinsBean>>() {
                    @Override
                    public void onNext(List<ExchangeCoinsBean> beans) {
                        if(view!=null){view.getMyCoinsExchangeSuccess(beans);}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        if(view!=null){view.getMyCoinsExchangeFail(e);}
                    }
                });
    }

    @Override
    public void ExchangeCoins(String id, String useraccountid, String alipay_num, String alipay_name) {
        model.ExchangeCoins(id, useraccountid, alipay_num, alipay_name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<Boolean>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if(view!=null){view.ExchangeCoinsSuccess();}
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                       if(view!=null){view.ExchangeCoinsFail(e);}
                    }
                });
    }
}
