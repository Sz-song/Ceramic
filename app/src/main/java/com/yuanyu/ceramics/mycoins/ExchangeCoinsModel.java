package com.yuanyu.ceramics.mycoins;

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
import okhttp3.RequestBody;

public class ExchangeCoinsModel implements ExchangeCoinsConstract.IExchangeCoinsModel {
    private HttpService httpService;
    ExchangeCoinsModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<List<ExchangeCoinsBean>>> getMyCoinsExchange(String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","get_mycoins_exchange");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getMyCoinsExchange(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> ExchangeCoins(String id, String useraccountid, String alipay_num, String alipay_name) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","exchange_coins");
        Map data = new HashMap();
        data.put("id",id);
        data.put("useraccountid",useraccountid);
        data.put("alipay_num",alipay_num);
        data.put("alipay_name",alipay_name);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.ExchangeCoins(body);
    }
}
