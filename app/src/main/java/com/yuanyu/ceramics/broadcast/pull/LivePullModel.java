package com.yuanyu.ceramics.broadcast.pull;

import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

public class LivePullModel implements LivePullConstract.ILivePullModel {
    private HttpService httpService;
    LivePullModel(){
        httpService = HttpServiceInstance.getInstance();
    }
}
