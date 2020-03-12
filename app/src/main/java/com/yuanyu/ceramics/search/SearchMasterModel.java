package com.yuanyu.ceramics.search;

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

public class SearchMasterModel implements SearchMasterConstract.ISearchMasterModel{
    private HttpService httpService;
    public SearchMasterModel(){httpService = HttpServiceInstance.getInstance();}

    @Override
    public Observable<BaseResponse<SearchBean>> SearchMasterList(int page, int useraccountid, int type, String search, int outsidetype) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","searchresult");
        Map data = new HashMap();
        data.put("page",page);
        data.put("pagesize","");
        data.put("intype",type);
        data.put("search",search);
        data.put("useraccountid",useraccountid);
        data.put("outtype",outsidetype);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("dashi data "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.getSearchDashiResult(body);
    }

    @Override
    public Observable<BaseResponse<Boolean>> Focus(int useraccountid, String userid) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","focusandchancel");
        Map data = new HashMap();
        data.put("useraccountid",useraccountid);
        data.put("userid",userid);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        L.e("str is "+str);
        RequestBody body=RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),str);
        return httpService.Focus(body);
    }
}
