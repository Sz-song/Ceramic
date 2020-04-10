package com.yuanyu.ceramics.seller.refund.refund_detail;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by cat on 2018/9/21.
 */

public class RefundDetailModel implements RefundDetailConstract.IRefundDetailModel{
    private HttpService httpService;
    RefundDetailModel(){
        httpService = HttpServiceInstance.getInstance();
    }

    @Override
    public Observable<BaseResponse<RefundDetailBean>> getRefundDetailData(String shopid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","refundmoremsg");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("ordernum",ordernum);

        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("refundmoremsg is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getRefundDetail(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> RefundReview(String shopid, String ordernum, int type, String reason) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","refundreview");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("ordernum",ordernum);
        data.put("type",type);
        data.put("reason",reason);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.RefundReview(body);
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
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getLogisticsTracing(body);
    }
}
