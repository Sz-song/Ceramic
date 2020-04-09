package com.yuanyu.ceramics.seller.message_shop;

import com.yuanyu.ceramics.base.BasePresenter;

public class MessageShopPresenter extends BasePresenter<MessageShopConstract.IMessageShopView> implements MessageShopConstract.IMessageShopPresenter {
    private MessageShopConstract.IMessageShopModel model;

    public MessageShopPresenter() {
        model= new MessageShopModel();
    }
}
