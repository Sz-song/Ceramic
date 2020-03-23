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

public class ShopDingzhiDetailModel implements ShopDingzhiDetailConstract.IShopDingzhiDetailModel {
    private HttpService httpService;
    ShopDingzhiDetailModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<DingzhiDetailBean>> dingzhiDetail(String dingzhi_id, String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "dingzhi_detail");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("dingzhi_id",dingzhi_id);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is"+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.dingzhiDetail(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> confirmOrder(String dingzhi_id, String shop_id, String price, String time) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "confirm_order");
        Map data = new HashMap();
        data.put("id",dingzhi_id);
        data.put("price",price);
        data.put("shop_id",shop_id);
        data.put("time",time);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is"+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.confirmDingzhiOrder(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> cancelOrder(String dingzhi_id, String shop_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "cancel_dingzhi_order");
        Map data = new HashMap();
        data.put("id",dingzhi_id);
        data.put("shop_id",shop_id);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is"+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.cancelDingzhiOrder(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> dingzhiCourier(String dingzhi_id, String shop_id, String courier_id, String courier_num) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "dingzhi_courier");
        Map data = new HashMap();
        data.put("id",dingzhi_id);
        data.put("shop_id",shop_id);
        data.put("courier_id",courier_id);
        data.put("courier_num",courier_num);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is"+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.dingzhiCourier(body);
    }
}
