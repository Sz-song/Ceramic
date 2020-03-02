package com.yuanyu.ceramics;

import com.tencent.mm.opensdk.openapi.IWXAPI;

public class AppConstant {
    public static String BASE_URL="http://101.132.128.125/song/";
    public static String QQ_APP_ID="1107922776";
    public static final String ACCOUNT = "account";//用户手机号
    public static final String MOBILE = "mobile";//用户手机号
    public static final String USER_NAME = "username";//用户昵称
    public static final String PASSWORD = "password";//用户密码
    public static final String SHOP_ID = "shop_id";//店铺id
    public static final String USER_ACCOUNT_ID = "useraccountid";//用户ID,用户在数据库索引id
    public static final String WECHAT_APP_ID = "wx4d8c30a755824341";
    public static final String SALT = "#jdkE;9djYUANYU";
    //我的订单
    public static final int ALL = 0;
    public static final int DAIFUKUAN = 1;
    public static final int DAIFAHUO = 2;
    public static final int DAISHOUHUO = 3;
    public static final int DAIPINGJIA = 4;
    public static final int YIQUXIAO = 6;
    public static final int TUIKUAN = 5;
    public static final int YICHANG = 7;
    public static final int YIPINGJIA = 8;
    public static final String STATUS = "status";
    //微博
    public static final String APP_KEY = "173905193";
    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";
    public static final String SCOPE = "";
    public static IWXAPI wx_api; //全局的微信api对象
}
