package com.yuanyu.ceramics.login;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

public class ResetPwdModel implements ResetPwdContract.IResetPwdModel {
    private HttpService httpService;
    ResetPwdModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<String[]>> resetPassword(String mobile, String validCode, String password) {
//        String timestamp = FileHelper.getTimeStamp();
////        String randomstr = FileHelper.getRandomString(10);
////        String signature = FileHelper.getSignature(timestamp,randomstr);
////        Map map = new HashMap();
////        map.put("timestamp",timestamp);
////        map.put("randomstr",randomstr);
////        map.put("signature",signature);
////        map.put("action","forgetpwd");
////        Map data = new HashMap();
////        data.put("mobile",mobile);
////        data.put("validate_code",validCode);
////        data.put("password",FileHelper.toMD5(FileHelper.toMD5(password)+AppConstant.SALT));
////        map.put("data",data);
////        Gson gson=new Gson();
////        String str=gson.toJson(map);
////        L.e("str is "+str);
////        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
////        return httpService.resetPwd(body);
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
//        return httpService.getValidCode(body);
        return null;
    }
}
