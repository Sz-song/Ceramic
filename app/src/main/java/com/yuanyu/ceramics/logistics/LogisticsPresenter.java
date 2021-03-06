package com.yuanyu.ceramics.logistics;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LogisticsPresenter extends BasePresenter<LogisticsConstract.ILogisticsTracingView> implements LogisticsConstract.ILogisticsTracingPresenter {
    private LogisticsConstract.ILogisticsTracingModel model;
    LogisticsPresenter() {
        model=new LogisticsModel();
    }
    @Override
    public void getLogisticsTracing(String couriernum, String courierid) {
        model.getLogisticsTracing(couriernum,courierid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<LogisticsBean>())
                .subscribe(new BaseObserver<LogisticsBean>() {
                    @Override
                    public void onNext(LogisticsBean logisticsBean) { if(view!=null){view.getLogisticsTracingSuccess(logisticsBean);} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.getLogisticsTracingFail(e);}}
                });
    }
}
