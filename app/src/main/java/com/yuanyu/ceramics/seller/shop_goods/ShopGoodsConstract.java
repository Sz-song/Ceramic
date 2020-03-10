package com.yuanyu.ceramics.seller.shop_goods;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ShopGoodsConstract {
    interface IShopGoodsModel {
        Observable<BaseResponse<List<com.yuanyu.ceramics.shop_index.ShopGoodsBean>>> getShopGoodsList(String shopid, int page, int type);
        Observable<BaseResponse<String[]>> shopGoodsDelete(String shopid, String id);
        Observable<BaseResponse<String[]>> shopGoodsOffShelves(String shopid, String id);
    }

    interface IShopGoodsView {
        void getShopGoodsSuccess(List<ShopGoodsBean> list);
        void shopGoodsDeleteSuccess(int position);
        void shopGoodsOffShelvesSuccess(int position);
        void getShopGoodsFail(ExceptionHandler.ResponeThrowable e);
        void shopGoodsDeleteFail(ExceptionHandler.ResponeThrowable e);
        void shopGoodsOffShelvesFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopGoodsPresenter {
        void getShopGoodsList(String shopid, int page, int type);
        void shopGoodsDelete(String shopid, String id, int position);
        void shopGoodsOffShelves(String shopid, String id, int position);
    }

}
