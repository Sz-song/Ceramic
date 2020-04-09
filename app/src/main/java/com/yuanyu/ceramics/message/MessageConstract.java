package com.yuanyu.ceramics.message;

import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

public interface MessageConstract {
    interface IMessageModel{

    }
    interface IMessageView{
        void initDataSuccess(List<MessageBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
        void receiveMessageSuccess(String msg,String sender);
    }
    interface IMessagePresenter{
        void initData();
    }
}
