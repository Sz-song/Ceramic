package com.yuanyu.ceramics.seller.liveapply;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LiveApplyModel implements LiveApplyConstract.ILiveApplyModel {
    private HttpService httpService;
    LiveApplyModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<List<String>>> uploadImage(List<File> images) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "upload");
        Map data = new HashMap();
        data.put("type", "2");
        data.put("postfix", "jpg");
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        MultipartBody.Part[] part = new MultipartBody.Part[images.size()];
        for (int i = 0; i < images.size(); i++) {
            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), images.get(i));
            part[i] = MultipartBody.Part.createFormData("files[]", images.get(i).getName(), photoBody);
        }
        return httpService.uploadImage(body,part);
    }

    @Override
    public Observable<BaseResponse<LiveApplyStatusBean>> getLiveApplyState(String shop_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","liveapplystate");
        Map data = new HashMap();
        data.put("shop_id",shop_id);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getLiveApplyState(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> liveApply(String id,String uid,String shop_id,String title,String cover,String time,List<String> item_list ) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","insert_live_application");
        Map data = new HashMap();
        data.put("id",id);
        data.put("useraccountid",uid);
        data.put("shop_id",shop_id);
        data.put("title",title);
        data.put("coverimg",cover);
        data.put("time",time);
        data.put("item_list",item_list);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.liveApply(body);
    }
}
