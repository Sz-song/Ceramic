package com.yuanyu.ceramics.order.refund;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface RefundListConstract {
    interface IRefundListModel {
        Observable<BaseResponse<List<RefundListBean>>> getRefundList(int useraccountid, int page);

    }

    interface IRefundListView {
        void getRefundListSuccess(List<RefundListBean> beans);
        void getRefundListFail(ExceptionHandler.ResponeThrowable e);

    }
    interface IRefundListPresenter {
        void getRefundList(int useraccountid, int page);
    }
}
