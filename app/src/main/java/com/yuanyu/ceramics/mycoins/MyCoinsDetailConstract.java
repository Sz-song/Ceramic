package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MyCoinsDetailConstract {
    interface IMyCoinsDetailModel{
        Observable<BaseResponse<List<MyCoinsDetailBean>>> getMyCoinsDetailList(String useraccountid, int page);
    }
    interface IMyCoinsDetailPresenter{
        void getMyCoinsDetailList(String useraccountid, int page);
    }
    interface IMyCoinsDetailView{
        void getMyCoinsDetailListSuccess(List<MyCoinsDetailBean> beans);
        void getMyCoinsDetailListFail(ExceptionHandler.ResponeThrowable e);
    }
}
