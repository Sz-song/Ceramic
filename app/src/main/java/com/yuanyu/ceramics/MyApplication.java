package com.yuanyu.ceramics;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        AppConstant.wx_api = WXAPIFactory.createWXAPI(context,AppConstant.WECHAT_APP_ID,false);
        AppConstant.wx_api.registerApp(AppConstant.WECHAT_APP_ID);
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            TIMSdkConfig config = new TIMSdkConfig(AppConstant.IM_APP_ID)
                    .enableLogPrint(true)
                    .setLogLevel(TIMLogLevel.DEBUG)
                    .setLogPath(getExternalFilesDir(null)+ "/IMLog/");
            //初始化 SDK
            TIMManager.getInstance().init(getApplicationContext(), config);
        }
    }
    public static Context getContext(){
        return context;
    }
}
