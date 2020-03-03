package com.yuanyu.ceramics.logistics;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface LogisticsConstract {
    interface ILogisticsTracingModel {
        Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum , String courierid);
    }
    interface ILogisticsTracingView {
        void getLogisticsTracingSuccess(LogisticsBean bean);
        void getLogisticsTracingFail(ExceptionHandler.ResponeThrowable e);
    }

    interface ILogisticsTracingPresenter {
        void getLogisticsTracing(String couriernum ,String courierid);
    }
}
