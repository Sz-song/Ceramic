package com.yuanyu.ceramics.seller.message_shop;

import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

public class MessageShopModel implements MessageShopConstract.IMessageShopModel {
    private HttpService httpService;
    MessageShopModel(){ httpService = HttpServiceInstance.getInstance();}
}
