package com.yuanyu.ceramics.shop_index;

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

public class ShopPinglunModel implements ShopPinglunConstract.IShopPinglunModel {
    private HttpService httpService;
    ShopPinglunModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<ShopPinglunBean>> getShopPinglun(String shopid, int page, int type) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","shop_pinglun");
        Map data = new HashMap();
        data.put("shop_id",shopid);
        data.put("page",page);
        data.put("page_size",10);
        data.put("type",type);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("post data is:"+str);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getShopPinglun(body);
    }
}
