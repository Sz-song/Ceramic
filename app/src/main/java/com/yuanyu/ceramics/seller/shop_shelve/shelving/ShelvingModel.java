package com.yuanyu.ceramics.seller.shop_shelve.shelving;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
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

/**
 * Created by cat on 2018/9/7.
 */

public class ShelvingModel implements ShelvingConstract.IShelvingModel{

    private HttpService httpService;
    public ShelvingModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<String[]>> Shelving(String shopid, ShelvingDetailBean bean) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","shangjiaapply");
        Map data = new HashMap();
        data.put("shopid",shopid);
        data.put("title",bean.getTitle());
        data.put("description",bean.getDescription());
        data.put("images",bean.getImages());
        data.put("artisan",bean.getArtisan());
        data.put("price",bean.getPrice());
        data.put("express_fee",bean.getFreight());
        data.put("fenlei",bean.getFenlei());
        data.put("zhonglei",bean.getZhonglei());
//        data.put("chanzhuang",bean.getChanzhuang());
//        data.put("pise",bean.getPise());
        data.put("ticai",bean.getTicai());
        data.put("weight",bean.getWeight());
        data.put("amount",bean.getAmount());
//        data.put("serial_no",bean.getSerial_no());
        data.put("length",bean.getLength());
        data.put("width",bean.getWidth());
        data.put("height",bean.getHeight());
        data.put("video",bean.getVideo());
        data.put("cover",bean.getVideo_cover());
        data.put("id","");
//        data.put("dd_price",bean.getDd_price());
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.Shelve(body);
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
    public Observable<BaseResponse<VideoBean>> uploadVideo(File video) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","upload");
        Map data = new HashMap();
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        MultipartBody.Part[] part = new MultipartBody.Part[1];
        RequestBody videoBody = RequestBody.create(MediaType.parse("video/mp4"), video);
        part[0] = MultipartBody.Part.createFormData("files[]", video.getName(), videoBody);

        return httpService.uploadVideo(body,part);
    }
}
