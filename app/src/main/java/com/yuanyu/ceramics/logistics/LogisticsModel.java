package com.yuanyu.ceramics.logistics;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class LogisticsModel implements LogisticsConstract.ILogisticsTracingModel {
    private HttpService httpService;
    LogisticsModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","express_tracking");
        Map data = new HashMap();
        data.put("couriernum",couriernum);
        data.put("courierid",courierid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getLogisticsTracing(body);
    }
}
