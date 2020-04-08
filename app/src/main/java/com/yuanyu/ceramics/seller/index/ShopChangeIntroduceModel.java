package com.yuanyu.ceramics.seller.index;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ShopChangeIntroduceModel implements ShopChangeIntroduceConstract.IShopChangeIntroduceModel {
    private HttpService httpService;
    public ShopChangeIntroduceModel(){
        httpService = HttpServiceInstance.getInstance();
    }
    @Override
    public Observable<BaseResponse<Boolean>> ShopChangeIntroduce(String shop_id, String introduce) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","change_shop_introduce");
        Map data = new HashMap();
        data.put("shop_id",shop_id);
        data.put("introduce",introduce);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.ShopChangeIntroduce(body);
    }
}
