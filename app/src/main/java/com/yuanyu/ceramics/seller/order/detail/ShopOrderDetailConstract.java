package com.yuanyu.ceramics.seller.order.detail;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ShopOrderDetailConstract {
    interface IShopOrderDetailModel{
        Observable<BaseResponse<ShopOrderDetailBean>> shopGetOrderDetail(String shopid, String ordernum);
        Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid);
        Observable<BaseResponse<Boolean>> modityOrderPrice(String shopid, String ordernum, String price);
    }

    interface IShopOrderDetailView{
        void shopGetOrderDetailSuccess(ShopOrderDetailBean bean);
        void shopGetOrderDetailFail(ExceptionHandler.ResponeThrowable e);
        void getLogisticsTracingSuccess(LogisticsBean bean);
        void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e);
        void modityOrderPriceSuccess(String price);
        void modityOrderPriceFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopOrderDetailPresenter{
        void shopGetOrderDetail(String shopid, String ordernum);
        void getLogisticsTracing(String couriernum, String courierid);
        void modityOrderPrice(String shopid, String ordernum, String price);
    }
}
