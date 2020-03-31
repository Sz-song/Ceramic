package com.yuanyu.ceramics.broadcast;

import com.yuanyu.ceramics.base.BaseResponse;

import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface BroadcastFragmentConstract {
    interface IBroadcastFragmentModel{
        Observable<BaseResponse<List<BroadcastBean>>> initData(String useraccountid,int page);
        Observable<BaseResponse<List<Boolean>>> subscribeLive(String useraccountid,String live_id);
    }
    interface IBroadcastFragmentView{
        void initDataSuccess(List<BroadcastBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);

        void subscribeLiveSuccess(Boolean issubscribe,int pos);
        void subscribeLiveFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IBroadcastFragmentPresenter{
        void initData(String useraccountid,int page);
        void subscribeLive(String useraccountid,String live_id,int pos);
    }
}
