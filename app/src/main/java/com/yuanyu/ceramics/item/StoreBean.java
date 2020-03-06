package com.yuanyu.ceramics.item;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-07-10.
 */

public class StoreBean implements Serializable {
        private int shop_id;
        private String studioheadimg;
        private String storename;
        private String yishounum ;
        private String pingfennum;
        private String guanzhunum;
        private String introduce;

    public StoreBean() {
    }

    public StoreBean(int shop_id, String studioheadimg, String storename, String yishounum, String pingfennum, String guanzhunum, String introduce) {
        this.shop_id = shop_id;
        this.studioheadimg = studioheadimg;
        this.storename = storename;
        this.yishounum = yishounum;
        this.pingfennum = pingfennum;
        this.guanzhunum = guanzhunum;
        this.introduce = introduce;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getStudioheadimg() {
        return studioheadimg;
    }

    public String getStorename() {
        return storename;
    }

    public String getYishounum() {
        return yishounum;
    }

    public String getPingfennum() {
        return pingfennum;
    }

    public String getGuanzhunum() {
        return guanzhunum;
    }

    public String getIntroduce() {
        return introduce;
    }
}
