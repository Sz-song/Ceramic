package com.yuanyu.ceramics.seller.index;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface IndexConstract {
    interface IMineModel{
        Observable<BaseResponse<IndexBean>> initData(String useraccountid);
    }
    interface IMineView{
        void initDataSuccess(IndexBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMinePresenter{
        void initData(String useraccountid);
    }
}
