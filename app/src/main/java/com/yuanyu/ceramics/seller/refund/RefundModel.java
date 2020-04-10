package com.yuanyu.ceramics.seller.refund;

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

/**
 * Created by cat on 2018/9/20.
 */

public class RefundModel implements RefundConstract.IRefundModel{
    private HttpService httpService;

    RefundModel(){
        httpService = HttpServiceInstance.getInstance();
    }

    @Override
    public Observable<BaseResponse<List<RefundBean>>> getRefundList(String shopid, int page, int type) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","refund");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("page",page);
        data.put("page_size","");
        data.put("type",type);

        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("refund is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getRefundResult(body);
    }
}
