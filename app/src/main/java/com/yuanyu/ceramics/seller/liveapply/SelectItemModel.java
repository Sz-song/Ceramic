package com.yuanyu.ceramics.seller.liveapply;

import com.google.gson.Gson;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.global.HttpService;
import com.yuanyu.ceramics.seller.liveapply.bean.SelectItemBean;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.Md5Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class SelectItemModel implements SelectItemConstract.ISelectItemModel {
    private HttpService httpService;
    SelectItemModel(){httpService = HttpServiceInstance.getInstance();}
    @Override
    public Observable<BaseResponse<SelectItemBean>> getSelectItem(String shop_id, List<String> selected_list, int page) {
        String timestamp = Md5Utils.getTimeStamp();
        String randomstr = Md5Utils.getRandomString(10);
        String signature = Md5Utils.getSignature(timestamp,randomstr);
        Map map = new HashMap();
        map.put("timestamp",timestamp);
        map.put("randomstr",randomstr);
        map.put("signature",signature);
        map.put("action","selectitem");
        Map data = new HashMap();
        data.put("page",page);
        data.put("page_size","");
        data.put("shopid",shop_id);
        data.put("list_commodity_id",selected_list);
        map.put("data",data);
        Gson gson=new Gson();
        String str=gson.toJson(map);
        RequestBody body=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),str);
        return null;
//        return httpService.selectItem(body);
    }
}
