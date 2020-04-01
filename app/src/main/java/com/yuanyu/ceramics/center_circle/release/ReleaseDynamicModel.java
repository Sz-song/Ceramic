package com.yuanyu.ceramics.center_circle.release;


import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ReleaseDynamicModel implements ReleaseDynamicConstract.IReleaseDynamicModel {
    //调接口需要用到
    private HttpService httpService;
    public ReleaseDynamicModel(){
        httpService= HttpServiceInstance.getInstance();
    }
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
    public Observable<BaseResponse<Boolean>> releaseDynamic(String id,String useraccountid, List<String> listimage, List<Integer> listfriends, boolean isopen, List<DynamicContentBean> listcontent) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","releasedynamic");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("listimage",listimage);
        data.put("listfriends",listfriends);
        data.put("isopen",isopen);
        data.put("listcontent",listcontent);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.ReleaseDynamic(body);
    }
    @Override
    public Observable<BaseResponse<Boolean>> savedraftsDynamic(String id, String useraccountid, List<DynamicContentBean> inputcontent, String inputstr, List<String> imglist, List<FriendBean> peoplelist, boolean isopen) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","save_dynamic");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",id);
        data.put("listcontent",inputcontent);
        data.put("inputstr",inputstr);
        data.put("listimage",imglist);
        data.put("listfriends",peoplelist);
        data.put("isopen",isopen);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.SaveDynamic(body);
    }

    @Override
    public Observable<BaseResponse<DraftsDynamic>> getDynamic(String id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","get_dynamic_draft");
        Map data = new HashMap();
        data.put("id",id);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getDynamic(body);
    }
}
