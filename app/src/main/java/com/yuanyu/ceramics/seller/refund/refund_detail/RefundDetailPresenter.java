package com.yuanyu.ceramics.seller.refund.refund_detail;

import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RefundDetailPresenter extends BasePresenter<RefundDetailConstract.IRefundDetailView> implements RefundDetailConstract.IRefundDetailPresenter {
    private RefundDetailConstract.IRefundDetailModel model;

    RefundDetailPresenter() {
        model=new RefundDetailModel();
    }

    @Override
    public void getRefundDetailData(String shopid, String ordernum) {
        model.getRefundDetailData(shopid,ordernum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<RefundDetailBean>>())
                .subscribe(new BaseObserver<RefundDetailBean>() {
                    @Override
                    public void onNext(RefundDetailBean bean) { if(view!=null){ view.getRefundDetailDataSuccess(bean);} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.getRefundDetailDataFail(e); } }
                });
    }

    @Override
    public void RefundReview(String shopid, String ordernum, int type, String reason) {
        model.RefundReview(shopid,ordernum,type,reason)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<BaseResponse<Boolean>>())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean b) { if(view!=null){ view.RefundReviewSuccess(b);} }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) { if(view!=null){ view.RefundReviewFail(e); } }
                });
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
