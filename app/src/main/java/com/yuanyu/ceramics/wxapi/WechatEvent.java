package com.yuanyu.ceramics.wxapi;

public class WechatEvent {
    private int code;
    private String transaction;
    private String openId;

    public WechatEvent(int code, String transaction, String openId) {
        this.code = code;
        this.transaction = transaction;
        this.openId = openId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
