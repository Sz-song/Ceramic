package com.yuanyu.ceramics;

import com.tencent.mm.opensdk.openapi.IWXAPI;

public class AppConstant {
    public static final String TOKEN ="token";
    public static final String REFRESH_TOKEN = "refresh_token";//30天过期
    public static String BASE_URL="http://132.232.95.104/";
    public static String QQ_APP_ID="1107922776";
    public static final String ACCOUNT = "account";//用户手机号
    public static final String MOBILE = "mobile";//用户手机号
    public static final String USER_NAME = "username";//用户昵称
    public static final String PASSWORD = "password";//用户密码
    public static final String SHOP_ID = "shop_id";//店铺id
    public static final String IS_MERCHANT = "isMerchant";//是否是商家
    public static final String SHOP_AVATAR = "shop_avatar";//店铺头像
    public static final String SHOP_NAME = "shop_name";//店铺名称
    public static final String USER_ACCOUNT_ID = "useraccountid";//用户ID,用户在数据库索引id
    public static final String WECHAT_APP_ID = "wx4d8c30a755824341";
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
