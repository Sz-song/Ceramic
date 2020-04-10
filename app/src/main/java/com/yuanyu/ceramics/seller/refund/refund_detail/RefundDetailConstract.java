package com.yuanyu.ceramics.seller.refund.refund_detail;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface RefundDetailConstract {
    interface IRefundDetailModel {
        Observable<BaseResponse<RefundDetailBean>> getRefundDetailData(String shopid, String ordernum);
        Observable<BaseResponse<Boolean>> RefundReview(String shopid, String ordernum, int type, String reason);
        Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid);
    }

    interface IRefundDetailView {
        void getRefundDetailDataSuccess(RefundDetailBean bean);
        void getRefundDetailDataFail(ExceptionHandler.ResponeThrowable e);

        void RefundReviewSuccess(Boolean b);
        void RefundReviewFail(ExceptionHandler.ResponeThrowable e);

        void getLogisticsTracingSuccess(LogisticsBean bean);
        void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IRefundDetailPresenter {
        void getRefundDetailData(String shopid, String ordernum);
        void RefundReview(String shopid, String ordernum, int type, String reason);
        void getLogisticsTracing(String couriernum, String courierid);
    }
}
