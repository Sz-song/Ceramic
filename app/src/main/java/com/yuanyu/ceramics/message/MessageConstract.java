package com.yuanyu.ceramics.message;

import com.tencent.imsdk.TIMMessage;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

import io.reactivex.Observable;

public interface MessageConstract {
    interface IMessageModel{
        Observable<BaseResponse<List<MessageBean>>> initData(List<String> useraccountidList);
    }
    interface IMessageView{
        void initDataSuccess(List<MessageBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
        void receiveMessageSuccess();
    }
    interface IMessagePresenter{
        void initData();
    }
}
