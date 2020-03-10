package com.yuanyu.ceramics.seller.delivery;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface WaitDeliveryConstract {
    interface IWaitDeliveryModel{
        Observable<BaseResponse<List<WaitDeliveryBean>>> getDeliveryOrdersResult(String shopid, int page);
    }

    interface IWaitDeliveryView{
        void setDataSuccess(List<WaitDeliveryBean> list);
        void setDataFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IWaitDeliveryPresenter{
        void getDeliveryOrdersResult(String shopid, int page);
    }
}
