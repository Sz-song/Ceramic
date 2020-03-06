package com.yuanyu.ceramics.item;

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

public class ItemDetailModel implements ItemDetailConstract.IItemDetailModel {
    private HttpService httpService;
    public ItemDetailModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<ItemDetailBean>> getItemDetail(String useraccountid, String itemid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","item_detail");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",itemid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.itemDetail(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> collectItem(String useraccountid, String itemid, int shopid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","add_collection");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",itemid);
        data.put("type",1);
        data.put("shop_id",shopid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.collectItem(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> addCart(String useraccountid, String itemid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","cart_add");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("itemid",itemid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.addCart(body);
    }

    @Override
    public Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(Integer page) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","loadmoreads");
        Map data = new HashMap();
        data.put("page",page);
        data.put("pagesize",30);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("loadmoreads is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.loadMoreAds(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> focus(String useraccountid, String userid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","focusandchancel");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("userid",userid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.Focus(body);
    }
}
