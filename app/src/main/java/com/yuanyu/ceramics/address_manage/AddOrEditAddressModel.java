package com.yuanyu.ceramics.address_manage;

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

public class AddOrEditAddressModel implements AddOrEditAddressConstract.IAddAddressModel {
    private HttpService httpService;
    public AddOrEditAddressModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<String[]>> addAddress(int useraccountid, String name, String phone, String province, String city, String exparea, String address, int isdefault) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","add_address");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("name",name);
        data.put("phone",phone);
        data.put("province",province);
        data.put("city",city);
        data.put("exparea",exparea);
        data.put("address",address);
        data.put("isdefault",isdefault);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.addAddress(body);

    }

    @Override
    public Observable<BaseResponse<String[]>> editAddress(int useraccountid, String id, String name, String phone, String province, String city, String exparea, String address, int isdefault) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","edit_address");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("id",id);
        data.put("name",name);
        data.put("phone",phone);
        data.put("province",province);
        data.put("city",city);
        data.put("exparea",exparea);
        data.put("address",address);
        data.put("isdefault",isdefault);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is:"+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.editAddress(body);
    }
}
