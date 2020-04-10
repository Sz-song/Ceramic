package com.yuanyu.ceramics.seller.refund;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface RefundConstract {
    interface IRefundModel {
        Observable<BaseResponse<List<RefundBean>>> getRefundList(String shopid, int page, int type);
    }

    interface IRefundView {
        void getRefundListSuccess(List<RefundBean> list);

        void getRefundListFail(ExceptionHandler.ResponeThrowable e);

    }

    interface IRefundPresenter {
        void getRefundList(String shopid, int page, int type);
    }
}
