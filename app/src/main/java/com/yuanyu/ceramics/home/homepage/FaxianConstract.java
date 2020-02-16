package com.yuanyu.ceramics.home.homepage;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface FaxianConstract {
    interface IFaxianModel{
        Observable<BaseResponse<HomepageBean>> initData(String useraccountid);
    }
    interface IFaxianView{
        void initDataSuccess(HomepageBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IFaxianPresenter{
        void initData(String useraccountid);
    }
}
