package com.yuanyu.ceramics.order;

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

public class MyOrderFragmentModel implements MyOrderFragmentConstract.IOrderFragmentModel{
    private HttpService httpService;
    MyOrderFragmentModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<List<MyOrderFragmentBean>>> getOrderList(String useraccountid, int page, int status) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","waitpay");
        Map data = new HashMap();
        data.put("page",page);
        data.put("page_size","");
        data.put("useraccountid",useraccountid);
        data.put("type",status);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getOrderList(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> cancelOrder(String uid, String order_num) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "cancelorder");
        Map data = new HashMap();
        data.put("useraccountid", uid);
        data.put("order_num", order_num);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.cancelOrder(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> deleteOrder(String uid, String order_num) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "deleteorder");
        Map data = new HashMap();
        data.put("useraccountid", uid);
        data.put("order_num", order_num);
        map.put("data", data);

        Gson gson = new Gson();
        String str = gson.toJson(map);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.deleteOrder(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> remindDelivery(String uid, String order_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "reminddelivery");
        Map data = new HashMap();
        data.put("uid",uid);
        data.put("id", order_id);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.remindDelivery(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> confirmReceived(String useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "receivedelivery");
        Map data = new HashMap();
        data.put("useraccountid", useraccountid);
        data.put("order_num", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.confirmReceived(body);
    }
}
