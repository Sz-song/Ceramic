package com.yuanyu.ceramics.common;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
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

public class CommonModel {
    private HttpService httpService;
    public CommonModel(){httpService = HttpServiceInstance.getInstance();}


    public Observable<BaseResponse<String[]>> submitReport(String useraccountid, String id, int type, String content, String contact, List<String> images){
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        /** useraccountid;int//举报人 id（防止恶意举报）
         * type:int//举报类型0动态，1帖子，2作品，3文章，4评论,5人，6群7商品，8拍品，9直播，10店铺（工作室）11.店铺恶意差评
         * id	int//被举报事物的id
         * contact:String//联系方式（可为空）
         * content:举报内容
         * list<String> images //举报的图片 }
         **/
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","report");
        Map data=new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",id);
        data.put("type",type);
        data.put("contact",contact);
        data.put("content",content);
        data.put("images",images);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is:"+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.submitReport(body);
    }
    //上传图片
    public Observable<BaseResponse<List<String>>> uploadImage(List<File> images){
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

}
