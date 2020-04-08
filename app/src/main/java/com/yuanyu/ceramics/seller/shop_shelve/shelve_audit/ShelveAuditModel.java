package com.yuanyu.ceramics.seller.shop_shelve.shelve_audit;

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
 * Created by cat on 2018/9/7.
 */

public class ShelveAuditModel implements ShelveAuditConstract.IShelveAuditModel{
    private HttpService httpService;
    public ShelveAuditModel(){httpService = HttpServiceInstance.getInstance();}


    @Override
    public Observable<BaseResponse<List<ShelveAuditBean>>> getShelveAuditData(String shopid, int type, int page) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","waitreview");
        Map data = new HashMap();
        data.put("page",page);
        data.put("page_size","");
        data.put("shopid",shopid);
        data.put("type",type);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getWaitReviewResult(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> deleteShelveAudit(String shopid, String id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","delete_commidity");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("id",id);
        data.put("type",2);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.DeleteResult(body);
    }
}
