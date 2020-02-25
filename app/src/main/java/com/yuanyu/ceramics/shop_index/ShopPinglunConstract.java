package com.yuanyu.ceramics.shop_index;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ShopPinglunConstract {

    interface IShopPinglunModel {
        Observable<BaseResponse<ShopPinglunBean>> getShopPinglun(String shopid, int page, int type);
    }

    interface IShopPinglunView {
        void getShopPinglunSuccess(ShopPinglunBean bean);
        void getShopPinglunFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopPinglunPresenter {
        void getShopPinglun(String shopid, int page, int type);
    }
}
