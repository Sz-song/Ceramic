package com.yuanyu.ceramics.login;

import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import io.reactivex.Observable;

public class RegisterModel implements RegisterContract.IRegisterModel {
    private HttpService httpService;
    RegisterModel(){ httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<String[]>> register(String mobile, String validCode, String userName, String password) {
//        String timestamp = FileHelper.getTimeStamp();
//        String randomstr = FileHelper.getRandomString(10);
//        String signature = FileHelper.getSignature(timestamp,randomstr);
//        Map map = new HashMap();
//        map.put("timestamp",timestamp);
//        map.put("randomstr",randomstr);
//        map.put("signature",signature);
//        map.put("action","register");
//        Map data = new HashMap();
//        data.put("mobile",mobile);
//        data.put("validate_code",validCode);
//        data.put("username",userName);
//        data.put("password",FileHelper.toMD5(FileHelper.toMD5(password)+AppConstant.SALT));
//        map.put("data",data);
//        Gson gson=new Gson();
//        String str=gson.toJson(map);
//        L.e("str is "+str);
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
//        return httpService.register(body);
        return null;
    }

    @Override
    public Observable<BaseResponse<String[]>> getValidCode(String mobile) {
//        String timestamp = FileHelper.getTimeStamp();
//        String randomstr = FileHelper.getRandomString(10);
//        String signature = FileHelper.getSignature(timestamp,randomstr);
//        Map map = new HashMap();
//        map.put("timestamp",timestamp);
//        map.put("randomstr",randomstr);
//        map.put("signature",signature);
//        map.put("action","getvalidatecode");
//        Map data = new HashMap();
//        data.put("mobile",mobile);
//        map.put("data",data);
//        Gson gson=new Gson();
//        String str=gson.toJson(map);
//        L.e("str is "+str);
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
//       return httpService.getValidCode(body);
        return null;
    }

    //查询昵称是否占用
    @Override
    public Observable<BaseResponse<Boolean>> viewUserName(String name) {
//        String timestamp = FileHelper.getTimeStamp();
//        String randomstr = FileHelper.getRandomString(10);
//        String signature = FileHelper.getSignature(timestamp,randomstr);
//        Map map = new HashMap();
//        map.put("timestamp",timestamp);
//        map.put("randomstr",randomstr);
//        map.put("signature",signature);
//        map.put("action","view_username");
//        Map data = new HashMap();
//        data.put("name",name);
//        map.put("data",data);
//        Gson gson=new Gson();
//        String str=gson.toJson(map);
//        L.e("str is "+str);
//        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
//        return httpService.viewUsername(body);
        return null;
    }
}
