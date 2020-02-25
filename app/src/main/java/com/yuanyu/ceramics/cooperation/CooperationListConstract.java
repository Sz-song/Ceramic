package com.yuanyu.ceramics.cooperation;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface CooperationListConstract {

    interface ICooperationListModel {
        Observable<BaseResponse<List<CooperationListBean>>> getCooperationList();
    }
    interface ICooperationListView {
        void getCooperationListSuccess(List<CooperationListBean> list);
        void getCooperationListFail(ExceptionHandler.ResponeThrowable e);
    }

    interface ICooperationListPresenter {
        void getCooperationList();
    }
}
