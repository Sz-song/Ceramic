package com.yuanyu.ceramics.login;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SplashModel implements SplashContract.ISplashModel {
    private HttpService httpService;
    SplashModel() { httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<LoginBean>> autoLogin(String token) {
        Map map = new HashMap();
        map.put("token",token);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.autoLogin(body);
    }

    @Override
    public Observable<BaseResponse<TokenBean>> refreshToken(String refresh_token) {
        Map map = new HashMap();
        map.put("refresh_token",refresh_token);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.refreshToken(body);
    }

}
