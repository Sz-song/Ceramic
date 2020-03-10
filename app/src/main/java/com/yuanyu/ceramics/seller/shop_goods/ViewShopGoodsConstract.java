package com.yuanyu.ceramics.seller.shop_goods;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface ViewShopGoodsConstract {
    interface IViewShopGoodsModel {
        Observable<BaseResponse<ShelvingDetailBean>> getShopGoodsDetail(String shopid, String id);
    }

    interface IViewShopGoodsView {
        void getShopGoodsDetailSuccess(ShelvingDetailBean shelvingDetailBean);
        void getShopGoodsDetailFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IViewShopGoodsPresenter {
        void getShopGoodsDetail(String shopid, String id);
    }
}
