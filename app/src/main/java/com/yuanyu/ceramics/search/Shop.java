package com.yuanyu.ceramics.search;

/**
 * Created by cat on 2018/7/26.
 */

public class Shop {

    private String portrait;
    private String name;
    private String yishou;
    private String haopin;
    private String fensi;
    private String shopid;


    public Shop(String portrait, String name, String yishou, String haopin, String fensi, String shopid) {
        this.portrait = portrait;
        this.name = name;
        this.yishou = yishou;
        this.haopin = haopin;
        this.fensi = fensi;
        this.shopid = shopid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
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

    public String getYishou() {
        return yishou;
    }

    public void setYishou(String yishou) {
        this.yishou = yishou;
    }

    public String getHaopin() {
        return haopin;
    }

    public void setHaopin(String haopin) {
        this.haopin = haopin;
    }

    public String getFensi() {
        return fensi;
    }

    public void setFensi(String fensi) {
        this.fensi = fensi;
    }
}
