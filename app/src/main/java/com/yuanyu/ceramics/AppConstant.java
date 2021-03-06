package com.yuanyu.ceramics;

import com.tencent.mm.opensdk.openapi.IWXAPI;

public class AppConstant {
    public static final String TOKEN ="token";
    public static final String REFRESH_TOKEN = "refresh_token";//30天过期
    public static final String USERSIG = "usersig";
    public static String BASE_URL="http://132.232.95.104/";
    public static final String WEBSITE = "https://www.jadeall.cn/";
    public static String QQ_APP_ID="1107922776";
    public static final String MOBILE = "mobile";//用户手机号
    public static final String PASSWORD = "password";//用户密码
    public static final String SHOP_ID = "shop_id";//店铺id
    public static final String USER_ACCOUNT_ID = "useraccountid";//用户ID,用户在数据库索引id
    public static final String USERNAME = "username";//用户昵称
    public static final String PROTRAIT = "protrait";//用户头像
    public static final String WECHAT_APP_ID = "wx4507b09176ec42f4";

    public static final int IM_APP_ID = 1400186194;//腾讯即时聊天app_id
    public static final String SALT = "#j@#*djYUANYU";
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
