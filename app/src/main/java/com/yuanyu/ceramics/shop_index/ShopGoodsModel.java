package com.yuanyu.ceramics.shop_index;

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

public class ShopGoodsModel implements ShopGoodsConstract.IShopGoodsModel {
    private HttpService httpService;
    ShopGoodsModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<List<ShopGoodsBean>>> getShopGoodsList(String shopid, int page, int type) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","shopcommodity");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("page",page);
        data.put("type",type);
        data.put("page_size",10);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("post data is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getShopCommodity(body);
    }
}
