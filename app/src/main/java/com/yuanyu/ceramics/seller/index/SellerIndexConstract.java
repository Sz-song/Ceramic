package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface SellerIndexConstract {
    interface IMineModel{
        Observable<BaseResponse<SellerIndexBean>> initData(String shopid);
    }
    interface IMineView{
        void initDataSuccess(SellerIndexBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMinePresenter{
        void initData(String shopid);
    }
}
