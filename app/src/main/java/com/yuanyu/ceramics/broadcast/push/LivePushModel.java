package com.yuanyu.ceramics.broadcast.push;

import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

public class LivePushModel implements LivePushConstract.ILivePushModel {
    private HttpService httpService;
    LivePushModel(){
        httpService = HttpServiceInstance.getInstance();
    }
}
