package com.yuanyu.ceramics.meet_master;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MeetMasterConstract {
    interface  IMeetMasterModel {
        Observable<BaseResponse<List<MeetMasterBean>>> initData(String useraccountid,int page);
    }
    interface IMeetMasterView {
        void initDataSuccess(List<MeetMasterBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
    }
    interface IMeetMasterPresenter {
        void initData(String useraccountid,int page);
    }
}
