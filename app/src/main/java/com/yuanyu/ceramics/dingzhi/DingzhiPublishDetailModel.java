package com.yuanyu.ceramics.dingzhi;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DingzhiPublishDetailModel implements DingzhiPublishDetailConstract.IDingzhiPublishDetailModel {
    private HttpService httpService;
    DingzhiPublishDetailModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<String[]>> dingzhiPublish(String useraccountid, String master_id, String detail, String useage,  int priceType, String fenlei, String zhonglei,String waiguan) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "publishdingzhi");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("master_id",master_id);
        data.put("detail",detail);
        data.put("useage",useage);
        data.put("zhonglei",zhonglei);
        data.put("priceType",priceType);
        data.put("fenlei",fenlei);
        data.put("waiguan",waiguan);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is"+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.publishDingzhi(body);
    }
}
