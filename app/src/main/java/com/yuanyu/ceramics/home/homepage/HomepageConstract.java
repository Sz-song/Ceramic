package com.yuanyu.ceramics.home.homepage;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import io.reactivex.Observable;

public interface HomepageConstract {
    interface IHomepageModel{
        Observable<BaseResponse<HomepageBean>> initData(String useraccountid);
    }
    interface IHomepageView{
        void initDataSuccess(HomepageBean bean);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IHomepagePresenter{
        void initData(String useraccountid);
    }
}
