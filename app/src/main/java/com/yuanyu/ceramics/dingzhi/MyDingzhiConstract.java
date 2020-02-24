package com.yuanyu.ceramics.dingzhi;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MyDingzhiConstract {
    interface IMyDingzhiModel {
        Observable<BaseResponse<List<MyDingzhiBean>>> getMyDingzhi(int page, int status, String id);
    }
    interface IMyDingzhiView {
        void getMyDingzhiSuccess(List<MyDingzhiBean> list);
        void getMyDingzhiFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IMyDingzhiPresenter {
        void getMyDingzhi(int page, int status, String id);
    }
}
