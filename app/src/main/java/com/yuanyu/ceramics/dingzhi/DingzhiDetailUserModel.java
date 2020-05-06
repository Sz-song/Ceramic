package com.yuanyu.ceramics.dingzhi;

import com.google.gson.Gson;
import com.yuanyu.ceramics.address_manage.AddressManageBean;
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

public class DingzhiDetailUserModel implements DingzhiDetailUserConstact.IDingzhiDetailUserModel {
    private HttpService httpService;
    DingzhiDetailUserModel(){httpService = HttpServiceInstance.getInstance();}
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
    public Observable<BaseResponse<GenerateOrdersBean>> generateBondOrder(String dingzhi_id, String useraccountid, int type, int paytype, AddressManageBean bean) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","generate_dingzhi_orders");
        Map data = new HashMap();
        data.put("dingzhi_id",dingzhi_id);
        data.put("useraccountid",useraccountid);
        data.put("type",type);
        data.put("paytype",paytype);
        if(type==1&&null!=bean){
            data.put("name",bean.getName());
            data.put("phone",bean.getPhone());
            data.put("province",bean.getProvince());
            data.put("city",bean.getCity());
            data.put("area",bean.getExparea());
            data.put("address",bean.getAddress());
        }
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.generateDingzhiBondOrder(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> BondPay(String id, int type, String out_trade_no, String trade_no) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","dingzhi_bondpay");
        Map data = new HashMap();
        data.put("id",id);
        data.put("type",type);
        data.put("out_trade_no",out_trade_no);
        data.put("trade_no",trade_no);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.dingzhiBondPay(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> confirm_receipt(String dingzhi_id, String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","dingzhi_receive");
        Map data = new HashMap();
        data.put("id",dingzhi_id);
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.dingzhiConfirmReceipt(body);
    }
}
