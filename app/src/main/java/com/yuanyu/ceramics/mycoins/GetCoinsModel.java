package com.yuanyu.ceramics.mycoins;

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

public class GetCoinsModel implements GetCoinsConstract.IGetCoinsModel {
    private HttpService httpService;
    GetCoinsModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<GetCoinsBean>> getGetCoinsTask(String useraccountid, String shop_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","get_mycoins_task");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("shop_id",shop_id);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getGetCoinsTask(body);
    }
}
