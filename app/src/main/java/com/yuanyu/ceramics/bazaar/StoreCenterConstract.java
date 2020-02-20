package com.yuanyu.ceramics.bazaar;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface StoreCenterConstract {
    interface IStoreCenterModel {
        Observable<BaseResponse<List<StoreCenterBean>>> getStoreCenterList(int page);
    }

    interface IStoreCenterView {
        void getStoreCenterSuccess(List<StoreCenterBean> list);
        void getStoreCenterFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IStoreCenterPresenter {
        void getStoreCenterList(int page);
    }
}
