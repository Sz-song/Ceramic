package com.yuanyu.ceramics.order.refund;



import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefundDetailWujiaPresenter extends BasePresenter<RefundDetailWujiaConstract.IRefundDetailView> implements RefundDetailWujiaConstract.IRefundDetailPresenter {
    private RefundDetailWujiaConstract.IRefundDetailModel model;

    public RefundDetailWujiaPresenter() {
        model=new RefundDetailWujiaModel();
    }

    @Override
    public void getRefundDetailData(String useraccountid, String ordernum) {
        model.getRefundDetailData(useraccountid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<RefundDetailBean>())
                .subscribe(new BaseObserver<RefundDetailBean>() {
                    @Override
                    public void onNext(RefundDetailBean bean) { if(view!=null){ view.getRefundDetailSuccess(bean); }}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.getRefundDetailFail(e);}}
                });
    }

//    @Override
//    public void getLogisticsTracing(String couriernum, String courierid) {
//        model.getLogisticsTracing(couriernum,courierid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(new HttpServiceInstance.ErrorTransformer<LogisticsBean>())
//                .subscribe(new BaseObserver<LogisticsBean>() {
//                    @Override
//                    public void onNext(LogisticsBean logisticsBean) { if(view!=null){view.getLogisticsTracingSuccess(logisticsBean);} }
//                    @Override
//                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.getLogisticsTracingFail(e);}}
//                });
//    }

    @Override
    public void CancelRefund(String useraccountid, String ordernum) {
        model.CancelRefund(useraccountid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) { if(view!=null){view.CancelRefundSuccess();} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.CancelRefundFail(e);}}
                });
    }

    @Override
    public void InputLogistics(String ordernum, String couriernum, String kuaidi) {
        model.InputLogistics(ordernum,couriernum,kuaidi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                .subscribe(new BaseObserver<String[]>() {
                    @Override
                    public void onNext(String[] strings) {if(view!=null){view.InputLogisticsSuccess();}}
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){view.InputLogisticsFail(e);}}
                });
    }
}
