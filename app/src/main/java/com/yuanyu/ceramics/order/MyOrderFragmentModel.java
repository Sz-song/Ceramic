package com.yuanyu.ceramics.order;

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

public class MyOrderFragmentModel implements MyOrderFragmentConstract.IOrderFragmentModel{
    private HttpService httpService;
    MyOrderFragmentModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<List<MyOrderFragmentBean>>> getOrderList(String useraccountid, int page, int status) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","waitpay");
        Map data = new HashMap();
        data.put("page",page);
        data.put("page_size",20);
        data.put("useraccountid",useraccountid);
        data.put("type",status);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getOrderList(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> cancelOrder(String uid, String order_num) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "cancelorder");
        Map data = new HashMap();
        data.put("useraccountid", uid);
        data.put("order_num", order_num);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.cancelOrder(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> deleteOrder(String uid, String order_num) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "deleteorder");
        Map data = new HashMap();
        data.put("useraccountid", uid);
        data.put("order_num", order_num);
        map.put("data", data);

        Gson gson = new Gson();
        String str = gson.toJson(map);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.deleteOrder(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> remindDelivery(String uid, String order_id) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "reminddelivery");
        Map data = new HashMap();
        data.put("uid",uid);
        data.put("id", order_id);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.remindDelivery(body);
    }

    @Override
    public Observable<BaseResponse<String[]>> confirmReceived(String useraccountid, String ordernum) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "receivedelivery");
        Map data = new HashMap();
        data.put("useraccountid", useraccountid);
        data.put("order_num", ordernum);
        map.put("data", data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.confirmReceived(body);
    }
    //提交订单评价
    public Observable<BaseResponse<String[]>> submitEvaluation(int review_type,String review_id,String comment,List<String>images,String shop_id,int rank,String commodity_id,int commodity_type,String uid,String order_num){
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp, randomstr);
        Map map = new HashMap();
        map.put("timestamp", timestamp);
        map.put("randomstr", randomstr);
        map.put("signature", signature);
        map.put("action", "evaluateitem");
        Map data = new HashMap();
        data.put("review_type",review_type);
        data.put("review_id",review_id);
        data.put("comment",comment);
        data.put("images",images);
        data.put("shop_id",shop_id);
        data.put("rank",rank);
        data.put("commodity_id",commodity_id);
        data.put("commodity_type",commodity_type);
        data.put("uid",uid);
        data.put("order_num",order_num);
        map.put("data",data);
        Gson gson = new Gson();
        String str = gson.toJson(map);
        L.e("str is "+str);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
        return httpService.submitEvulation(body);
    }
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
