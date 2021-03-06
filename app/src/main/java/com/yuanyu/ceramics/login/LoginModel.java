package com.yuanyu.ceramics.login;

import com.google.gson.Gson;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class LoginModel implements LoginContract.ILoginModel {
    private HttpService httpService;
    LoginModel(){
        httpService = HttpServiceInstance.getInstance();
    }

    @Override
    public Observable<BaseResponse<LoginBean>> login(int type, String mobile, String password) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","login");
        Map data = new HashMap();
        data.put("mobile",mobile);
        if(type==5){
            data.put("password",password);
        }else {
            data.put("password", Md5Utils.toMD5(Md5Utils.toMD5(password) + AppConstant.SALT));
        }
        data.put("type",type);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.login(body);

    }

    @Override
    public Observable<BaseResponse<String[]>> getValidCode(String mobile) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","getvalidatecode");
        Map data = new HashMap();
        data.put("mobile",mobile);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getValidCode(body);
    }

    @Override
    public Observable<BaseResponse<LoginBean>> thirdLogin(String status, String portrait, String nickname, String openid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","login");
        Map data = new HashMap();
        data.put("type",status);
        data.put("portrait",portrait);
        data.put("openid",openid);
        data.put("name",nickname);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.thirdLogin(body);
    }
}
