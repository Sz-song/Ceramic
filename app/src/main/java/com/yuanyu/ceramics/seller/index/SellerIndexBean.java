package com.yuanyu.ceramics.seller.index;

public class SellerIndexBean {
    private String portrait;
    private String name;
    private String introduce; //店铺简介
    private int nopay; //未支付
    private int fahuo;//未发货
    private int refund;//退款售后
    private boolean isbind_wechat;
    private boolean isbind_ali;

    public SellerIndexBean(String portrait, String name, String introduce, int nopay, int fahuo, int refund, boolean isbind_wechat, boolean isbind_ali) {
        this.portrait = portrait;
        this.name = name;
        this.introduce = introduce;
        this.nopay = nopay;
        this.fahuo = fahuo;
        this.refund = refund;
        this.isbind_wechat = isbind_wechat;
        this.isbind_ali = isbind_ali;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getNopay() {
        return nopay;
    }

    public void setNopay(int nopay) {
        this.nopay = nopay;
    }

    public int getFahuo() {
        return fahuo;
    }

    public void setFahuo(int fahuo) {
        this.fahuo = fahuo;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    public boolean isIsbind_wechat() {
        return isbind_wechat;
    }

    public void setIsbind_wechat(boolean isbind_wechat) {
        this.isbind_wechat = isbind_wechat;
    }

    public boolean isIsbind_ali() {
        return isbind_ali;
    }

    public void setIsbind_ali(boolean isbind_ali) {
        this.isbind_ali = isbind_ali;
    }
}
