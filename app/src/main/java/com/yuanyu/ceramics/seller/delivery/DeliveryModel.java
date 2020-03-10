package com.yuanyu.ceramics.seller.delivery;

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

public class DeliveryModel implements DeliveryConstract.IDeliveryModel {
    private HttpService httpService;

    DeliveryModel() {
        httpService = HttpServiceInstance.getInstance();
    }

    @Override
    public Observable<BaseResponse<DeliveryBean>> getDeliveryMsg(String ordernum, String shop_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "getdeliverymsg");
        Map data = new HashMap();
        data.put("ordernum", ordernum);
        data.put("shopid", shop_id);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is " + str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.getDeliveryData(body);
    }

    public Observable<BaseResponse<String>> AutoDelivery(String shop_id, DeliveryBean bean) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "shopdelivery");
        Map data = new HashMap();
        data.put("shop_id", shop_id);
        data.put("ordernum", bean.getOrdernum());
        data.put("courierid", bean.getCourierid());
        data.put("couriernum", bean.getCourier_num());
        data.put("note", bean.getNote());
        data.put("useraccountid", bean.getUseraccountid());
        data.put("sender_name", bean.getSend_name());
        data.put("sender_phone", bean.getSend_tel());
        data.put("sender_address", bean.getSend_address());
        data.put("sender_province", bean.getSend_province());
        data.put("sender_city", bean.getSend_city());
        data.put("sender_exparea", bean.getSend_area());
        data.put("commodity_name", bean.getCommodity_name());
        data.put("commodity_id", bean.getCommodity_id());
        data.put("receiver_name", bean.getReceive_name());
        data.put("receiver_phone", bean.getReceive_tel());
        data.put("receiver_province", bean.getReceive_province());
        data.put("receiver_city", bean.getReceive_city());
        data.put("receiver_exparea", bean.getReceive_area());
        data.put("receiver_address", bean.getReceive_address());
        data.put("end_date", bean.getTime_end());//yyyy-MM-dd HH:mm:ss
        data.put("start_date", bean.getTime_start());//yyyy-MM-dd HH:mm:ss
        data.put("pay_type", bean.getCourier_paytype());
        data.put("remark", bean.getRemark());
        data.put("commodity_weight", bean.getCourier_weight());
        data.put("status", 1);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is " + str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.Delivery(body);
    }

    @Override
    public Observable<BaseResponse<String>> HandDelivery(String shop_id, DeliveryBean bean) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "shopdelivery");
        Map data = new HashMap();
        data.put("shop_id", shop_id);
        data.put("ordernum", bean.getOrdernum());
        data.put("courierid", bean.getCourierid());
        data.put("couriernum", bean.getCourier_num());
        data.put("useraccountid", bean.getUseraccountid());
        data.put("commodity_name", bean.getCommodity_name());
        data.put("commodity_id", bean.getCommodity_id());
        data.put("receiver_name", bean.getReceive_name());
        data.put("receiver_phone", bean.getReceive_tel());
        data.put("receiver_province", bean.getReceive_province());
        data.put("receiver_city", bean.getReceive_city());
        data.put("receiver_exparea", bean.getReceive_area());
        data.put("receiver_address", bean.getReceive_address());
        data.put("status", 2);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is " + str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.Delivery(body);
    }
}
