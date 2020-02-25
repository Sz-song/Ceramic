package com.yuanyu.ceramics.shop_index;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.ResultBean;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;


public class ShopIndexModel implements ShopIndexConstract.IShopIndexModel{
    private HttpService httpService;
    public ShopIndexModel(){httpService = HttpServiceInstance.getInstance();}

    public Observable<BaseResponse<List<ResultBean>>> getShopFenleiData(int page, String feilei, String zhonglei, String pise, String ticai, String chanzhaung, String min_price, String max_price, String min_weight, String max_weight, String shop_id){
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","shop_fenlei");
        Map data = new HashMap();
        data.put("page", page);
        data.put("page_size",10);
        data.put("fenlei",feilei);
        data.put("zhonglei",zhonglei);
        data.put("pise",pise);
        data.put("ticai",ticai);
        data.put("chanzhuang",chanzhaung);
        data.put("min_price",min_price);
        data.put("max_price",max_price );
        data.put("min_weight",min_weight);
        data.put("max_weight",max_weight);
        data.put("shop_id",shop_id);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("data "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getShopFenleiData(body);

    }

    @Override
    public Observable<BaseResponse<ShopIndexBean>> getShopIndexData(String shop_id, String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","shophead");
        Map data = new HashMap();
        data.put("shopid",shop_id);
        data.put("useraccountid",useraccountid);
        map.put("data",data);

        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("data "+str);

        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getUserShopIndexResult(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> focusShop(int type, String shop_id, String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","add_collection");
        Map data = new HashMap();
        data.put("type",type);
        data.put("id",shop_id);
        data.put("shop_id",shop_id);
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("post data is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.collectItem(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> shareShop(String shop_id, String useraccountid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","share_shop");
        Map data = new HashMap();
        data.put("shop_id",shop_id);
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("post data is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.shareShop(body);
    }
}
