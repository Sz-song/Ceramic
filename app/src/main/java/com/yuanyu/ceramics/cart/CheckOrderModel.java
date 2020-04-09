package com.yuanyu.ceramics.cart;

import com.google.gson.Gson;
import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class CheckOrderModel implements CheckOrderConstract.ICheckOrderModel {
    private HttpService httpService;
    CheckOrderModel(){httpService = HttpServiceInstance.getInstance();}


    @Override
    public Observable<BaseResponse<List<AddressManageBean>>> getAddressData(String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","address");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getAddressData(body);
    }

    @Override
    public Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(int page) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","loadmoreads");
        Map data = new HashMap();
        data.put("page",""+page);
        data.put("page_size",30);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("loadmoreads is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.loadMoreAds(body);
    }

    @Override
    public Observable<BaseResponse<GenerateOrdersBean>> generateOrders(String useraccountid, int paytype, int type, int tag, String name, String address, String province, String city, String area, String tel, List<SumOrderBean> list) {
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
        data.put("list",list);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.generateOrders(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> sendAliPay(String useraccountid, List<String> order_list, String out_trade_no, String trade_no) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","makepayment");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("order_list",order_list);
        data.put("out_trade_no",out_trade_no);
        data.put("trade_no",trade_no);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.sendAliPay(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> notify_order_exception(List<String> order_list, String useraccountid, String out_trade_no, String trade_no) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","exception_order");
        Map data = new HashMap();
        data.put("id",useraccountid);
        data.put("list",order_list);
        data.put("out_trade_no",out_trade_no);
        data.put("trade_no",trade_no);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.notify_order_exception(body);
    }
}
