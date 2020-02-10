package com.yuanyu.ceramics.broadcast;

import com.yuanyu.ceramics.base.BaseResponse;

import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface BroadcastFragmentConstract {
    interface IBroadcastFragmentModel{
        Observable<BaseResponse<List<BroadcastBean>>> initData(String useraccountid);
    }
    interface IBroadcastFragmentView{
        void initDataSuccess(List<BroadcastBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IBroadcastFragmentPresenter{
        void initData(String useraccountid);
    }
}
