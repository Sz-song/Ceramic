package com.yuanyu.ceramics.order.refund;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface RefundDetailWujiaConstract {
    interface IRefundDetailModel {
        Observable<BaseResponse<RefundDetailBean>> getRefundDetailData(String useraccountid, String ordernum);
//        Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid);
        Observable<BaseResponse<String[]>> CancelRefund(String useraccountid, String ordernum);
        Observable<BaseResponse<String[]>> InputLogistics(String ordernum, String couriernum, String kuaidi);
    }
    interface IRefundDetailView {
        void getRefundDetailSuccess(RefundDetailBean bean);
        void getRefundDetailFail(ExceptionHandler.ResponeThrowable e);

//        void getLogisticsTracingSuccess(LogisticsBean bean);
//        void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e);

        void CancelRefundSuccess();
        void CancelRefundFail(ExceptionHandler.ResponeThrowable e);

        void InputLogisticsSuccess();
        void InputLogisticsFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IRefundDetailPresenter {
        void getRefundDetailData(String useraccountid, String ordernum);
//        void getLogisticsTracing(String couriernum, String courierid);
        void CancelRefund(String useraccountid, String ordernum);
        void InputLogistics(String ordernum, String couriernum, String kuaidi);
    }
}
