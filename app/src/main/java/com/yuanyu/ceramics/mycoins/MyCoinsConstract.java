package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface MyCoinsConstract {
    interface IMyCoinsModel {
        Observable<BaseResponse<String>> getMyCoinsDate(String useraccountid);
    }
    interface IMyCoinsView {
        void getMyCoinsDateSuccess(String num);
        void getMyCoinsDateFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IMyCoinsPresenter {
        void getMyCoinsDate(String useraccountid);
    }
}
