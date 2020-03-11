package com.yuanyu.ceramics.fenlei;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.common.ResultBean;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2018-07-30.
 */

public class FenleiModel implements FenLeiConstract.IFenleiModel{
    private HttpService httpService;

    FenleiModel(){
        httpService = HttpServiceInstance.getInstance();
    }

    Observable<BaseResponse<List<ResultBean>>> getFenleiRusult(String[] strings, int page, int pagesize) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","getfenleiresult");
        Map<String, Serializable> data = new HashMap<>();
        data.put("page",page);
        data.put("pagesize",pagesize);
        data.put("fenlei",strings[0]);
        data.put("zhonglei",strings[1]);
        data.put("pise",strings[2]);
        data.put("ticai",strings[3]);
        data.put("chanzhuang",strings[4]);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getFenleiResult(body);
    }

}
