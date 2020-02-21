package com.yuanyu.ceramics.dingzhi;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface ShopDingzhiFragmentConstract {
    interface IShopDingzhiModel {
        Observable<BaseResponse<List<MyDingzhiBean>>> getShopDingzhi(int page, int type, String shop_id);
    }
    interface IShopDingzhiView {
        void getShopDingzhiSuccess(List<MyDingzhiBean> list);
        void getShopDingzhizhiFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IShopDingzhiPresenter {
        void getShopDingzhi(int page, int status, String shop_id);
    }
}
