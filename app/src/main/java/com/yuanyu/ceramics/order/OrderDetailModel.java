package com.yuanyu.ceramics.order;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.cart.GenerateOrdersBean;
import com.yuanyu.ceramics.cart.SumOrderBean;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class OrderDetailModel implements OrderDetailConstract.IOrderDetailModel{
    private HttpService httpService;
    OrderDetailModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<OrderDetailBean>> getOrderDetail(String useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "ordermoremsg");
        Map data = new HashMap();
        data.put("useraccountid", useraccountid);
        data.put("ordernum", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.getOrderDetailData(body);
    }

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
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getLogisticsTracing(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> cancelOrder(String useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "cancelorder");
        Map data = new HashMap();
        data.put("useraccountid", useraccountid);
        data.put("order_num", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.cancelOrder(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> comfirmReceived(String useraccountid, String ordernum) {
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

    @Override
    public Observable<BaseResponse<GenerateOrdersBean>> generateOrders(String useraccountid, int paytype, int type, int tag, int qiugou_id, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","generate_orders");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("name",name);
        data.put("address",address);
        data.put("province",province);
        data.put("city",city);
        data.put("area",area);
        data.put("tel",tel);
        data.put("paytype",paytype);
        data.put("type",type);
        data.put("tag", tag);
        data.put("qiugou_id",qiugou_id);
        data.put("list",list);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.generateOrders(body);
    }

}
