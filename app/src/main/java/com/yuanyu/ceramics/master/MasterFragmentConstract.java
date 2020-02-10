package com.yuanyu.ceramics.master;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MasterFragmentConstract {
    interface IMasterFragmentModel {
        Observable<BaseResponse<List<MasterItemBean>>> initData(String useraccountid);
    }
    interface IMasterFragmentView {
        void initDataSuccess(List<MasterItemBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMasterFragmentPresenter {
        void initData(String useraccountid);
    }
}
