package com.yuanyu.ceramics.seller.delivery;

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

public class CourierModel implements CourierConstract.ICourierModel{
    private HttpService httpService;
    public  CourierModel (){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<List<CourierBean>>> getCourierData(String status, DeliveryBean bean) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","ExpRecommend");
        Map data = new HashMap();
        data.put("status",status);
        data.put("order_code",bean.getOrdernum());
        data.put("sender_province_name",bean.getSend_province());
        data.put("sender_city_name",bean.getSend_city());
        data.put("sender_exparea_name",bean.getSend_area());
        data.put("sender_address",bean.getSend_address());
        data.put("receiver_province_name",bean.getReceive_province());
        data.put("receiver_city_name",bean.getReceive_city());
        data.put("receiver_exparea_name",bean.getReceive_area());
        data.put("receiver_address",bean.getReceive_address());
        data.put("goods_names",bean.getCommodity_name());
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getCourierData(body);
    }

    @Override
    public Observable<BaseResponse<List<CourierBean>>> getCourierCompany() {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","expresscompany");
        Map data = new HashMap();
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getCourierCompany(body);
    }
}
