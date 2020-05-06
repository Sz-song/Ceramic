package com.yuanyu.ceramics.seller.evaluationmanage;

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
 * Created by Administrator on 2018-08-22.
 */

public class EvaluationManageModel {
    private HttpService httpService;
    public EvaluationManageModel(){httpService = HttpServiceInstance.getInstance();}
    public Observable<BaseResponse<List<EvaluationManageBean>>> getEvaluation(String shopid, int page, int page_size, int type){
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","evaluationmanage");
        Map data = new HashMap();
        data.put("shop_id",shopid);
        data.put("page",page);
        data.put("page_size",page_size);
        data.put("type",type);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getEvaluation(body);
    }
    public Observable<BaseResponse<String[]>> replyEvaluation(String shopid, String id, int type, String txt){
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","reply_evaluation");
        Map data = new HashMap();
        data.put("shop_id",shopid);
        data.put("id",id);
        data.put("type",type);
        data.put("txt",txt);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.replyEvaluation(body);
    }
}
