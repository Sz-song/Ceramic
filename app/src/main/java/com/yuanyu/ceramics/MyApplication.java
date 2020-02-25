package com.yuanyu.ceramics;

import android.app.Application;
import android.content.Context;

import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppConstant.wx_api = WXAPIFactory.createWXAPI(context,AppConstant.WECHAT_APP_ID,false);
        AppConstant.wx_api.registerApp(AppConstant.WECHAT_APP_ID);
    }
    public static Context getContext(){
        return context;
    }
}
