package com.yuanyu.ceramics.dingzhi;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyDingzhiModel implements MyDingzhiConstract.IMyDingzhiModel {
    private HttpService httpService;
    MyDingzhiModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<List<MyDingzhiBean>>> getMyDingzhi(int page, int status, String id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","getmydingzhi");
        Map data = new HashMap();
        data.put("page",page);
        data.put("page_size",10);
        data.put("uid",id);
        data.put("status",status);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("mydingzhi "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getMyDingzhi(body);
    }
}
