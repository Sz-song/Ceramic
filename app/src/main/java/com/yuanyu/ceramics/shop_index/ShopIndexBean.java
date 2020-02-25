package com.yuanyu.ceramics.shop_index;

/**
 * Created by cat on 2018/9/3.
 */

public class ShopIndexBean {
    private String id;
    private String bg;        //店铺背景
    private String portrait;  //店铺头像
    private String name;      //店铺名
    private String fensi_num;  //粉丝数量
    private int iscollect; //0未关注 1已关注
    private String location;   //地址
    private boolean ismaster;//是否大师店铺


    public String getId() {
        return id;
    }

    public String getBg() {
        return bg;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getFensi_num() {
        return fensi_num;
    }

    public int getIscollect() {
        return iscollect;
    }

    public String getLocation() {
        return location;
    }

    public boolean isIsmaster() {
        return ismaster;
    }
}
