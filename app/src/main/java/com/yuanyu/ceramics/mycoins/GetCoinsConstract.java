package com.yuanyu.ceramics.mycoins;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface GetCoinsConstract {
    interface IGetCoinsModel {
        Observable<BaseResponse<GetCoinsBean>> getGetCoinsTask(String useraccountid, String shop_id);
    }
    interface IGetCoinsView {
        void getMyCoinsTaskSuccess(GetCoinsBean bean);
        void getMyCoinsTaskFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IGetCoinsPresenter {
        void getMyCoinsTask(String useraccountid, String shop_id);
    }
}
