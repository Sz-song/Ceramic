package com.yuanyu.ceramics.seller.order;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ShopOrderConstract {
    interface IShopOrderModel {
        Observable<BaseResponse<List<ShopOrderBean>>> getOrderList(String shopid, int page, int type);
    }

    interface IShopOrderView {
        void getOrderListSuccess(List<ShopOrderBean> list);
        void getOrderListFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopOrderPresenter {
        void getOrderList(String shopid, int page, int type);
    }
}
