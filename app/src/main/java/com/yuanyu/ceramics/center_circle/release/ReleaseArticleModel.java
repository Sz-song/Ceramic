package com.yuanyu.ceramics.center_circle.release;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.center_circle.detail.ArticleContentBean;
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

public class ReleaseArticleModel implements ReleaseArticleConstract.IReleaseArticleModel {
    private HttpService httpService;
    public ReleaseArticleModel(){httpService = HttpServiceInstance.getInstance();}
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
    public Observable<BaseResponse<Boolean>> releaseArticle(String id,String cover, String title, String useraccountid, List<ArticleContentBean> content) {

        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","releasearticle");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",id);
        data.put("cover",cover);
        data.put("title",title);
        data.put("content",content);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.ReleaseArticle(body);
    }
    @Override
    public Observable<BaseResponse<Boolean>> saveArticle(String id,String cover, String title, String useraccountid, List<ArticleContentBean> content) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","drafts_article");
        Map data = new HashMap();
        data.put("id",id);
        data.put("useraccountid",useraccountid);
        data.put("cover",cover);
        data.put("title",title);
        data.put("content",content);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.SaveArticle(body);
    }

    @Override
    public Observable<BaseResponse<DraftsArticle>> getArticle(String id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","get_drafts_articledetail");
        Map data = new HashMap();
        data.put("id",id);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getArticle(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> deleteArticle(String useraccountid,String id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","delete_drafts");
        Map data = new HashMap();
        data.put("id",id);
        data.put("type",1);
        data.put("useraccountid",useraccountid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.deleteDraftsDynamic(body);
    }
}
