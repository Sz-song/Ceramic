package com.yuanyu.ceramics.seller.index;

public class SellerIndexBean {
    private int shopid;
    private String portrait;
    private String name;
    private String introduce; //店铺简介

    public int getShopid() {
        return shopid;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public SellerIndexBean(int shopid, String portrait, String name, String introduce) {
        this.shopid = shopid;
        this.portrait = portrait;
        this.name = name;
        this.introduce = introduce;
    }
}
