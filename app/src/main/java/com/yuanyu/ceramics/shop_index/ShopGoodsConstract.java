package com.yuanyu.ceramics.shop_index;



import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ShopGoodsConstract {
    interface IShopGoodsModel {
        Observable<BaseResponse<List<ShopGoodsBean>>> getShopGoodsList(String shopid, int page, int type);
    }

    interface IShopGoodsView {
        void getShopGoodsSuccess(List<ShopGoodsBean> list);
        void getShopGoodsFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopGoodsPresenter {
        void getShopGoodsList(String shopid, int page, int type);
    }
}
