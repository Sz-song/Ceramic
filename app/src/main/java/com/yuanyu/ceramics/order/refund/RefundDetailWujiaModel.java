package com.yuanyu.ceramics.order.refund;

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

public class RefundDetailWujiaModel implements RefundDetailWujiaConstract.IRefundDetailModel {
    private HttpService httpService;
    public RefundDetailWujiaModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<RefundDetailBean>> getRefundDetailData(int useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "refundwujiamoremsg");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("refund_num", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.refundDetail(body);
    }

//    @Override
//    public Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(String couriernum, String courierid) {
//        String timestamp = Md5Utils.getTimeStamp();
//        String randomstr = Md5Utils.getRandomString(10);
//        String signature = Md5Utils.getSignature(timestamp,randomstr);
//        Map map = new HashMap();
//        map.put("timestamp",timestamp);
//        map.put("randomstr",randomstr);
//        map.put("signature",signature);
//        map.put("action","express_tracking");
//        Map data = new HashMap();
//        data.put("couriernum",couriernum);
//        data.put("courierid",courierid);
//        map.put("data",data);
//        Gson gson=new Gson();
//        String str=gson.toJson(map);
//        L.e("str is "+str);
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
//        return httpService.getLogisticsTracing(body);
//    }

    @Override
    public Observable<BaseResponse<String[]>> CancelRefund(int useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "cancel_refund");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("order_num", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.CancelRefund(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> InputLogistics(String ordernum, String couriernum, String kuaidi) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "add_refund_logistics");
        Map data = new HashMap();
        data.put("ordernum",ordernum);
        data.put("couriernum", couriernum);
        data.put("kuaidi", kuaidi);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.InputLogistics(body);
    }
}
