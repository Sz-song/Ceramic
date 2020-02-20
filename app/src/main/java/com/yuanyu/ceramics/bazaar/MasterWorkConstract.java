package com.yuanyu.ceramics.bazaar;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;


public interface MasterWorkConstract {
    interface IMasterWorkModel {
        Observable<BaseResponse<List<MasterWorkBean>>> getMasterWorkList(int page);
    }

    interface IMasterWorkView {
        void getMasterWorkSuccess(List<MasterWorkBean> list);
        void getMasterWorkFail(ExceptionHandler.ResponeThrowable e);
    }

    interface IMasterWorkPresenter {
        void getMasterWorkList(int page);
    }
}
