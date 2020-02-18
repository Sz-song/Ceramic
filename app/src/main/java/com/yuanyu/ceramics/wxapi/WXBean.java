package com.yuanyu.ceramics.wxapi;

public class WXBean {
    private String access_token;
    private String headimgurl;//  微信用户头像地址
    private String nickname;//  微信用户昵称
    private String openid;
    private String refresh_token;
    private String unionId;

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getUnionId() {
        return unionId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public String getOpenid() {
        return openid;
    }
}
