package com.yuanyu.ceramics.seller.message_shop;

import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;

import java.util.List;

public interface MessageShopConstract {
    interface IMessageShopModel{

    }
    interface IMessageShopView{
        void initDataSuccess(List<MessageBean> list);
        void initDataFail(ExceptionHandler.ResponeThrowable e);
        void receiveMessageSuccess(String msg,String sender);
    }
    interface IMessageShopPresenter{
       void  initData();
    }
}
