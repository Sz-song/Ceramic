package com.yuanyu.ceramics.cart;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface CartConstract {
    interface ICartModel{
        Observable<BaseResponse<List<GoodsBean>>> getGoodsdata(String useraccountid);
        Observable<BaseResponse<String[]>> deleteCartItem(String useraccountid, String itemid);
    }
    interface ICartView{
        void getGoodsdataSuccess(List<GoodsBean> beans);
        void getGoodsdataFail(ExceptionHandler.ResponeThrowable e);

        void deleteCartItemSuccess(String id);
        void deleteCartItemFail(ExceptionHandler.ResponeThrowable e);
    }
    interface ICartPresenter{
        void getGoodsdata(String useraccountid);
        void initList(List<CartBean> list,List<GoodsBean>goodsBeans);
        void deleteCartItem(String useraccountid,String id);
        void initpayList(List<CartBean> list,List<GoodsBean> payList);
    }
}
