package com.yuanyu.ceramics.meet_master;

import java.util.List;

public class MeetMasterBean {

    private String useraccountid;
    private String shop_id;
    private String shop_name;
    private String shop_avatar;
    private int focusnum;
    private int fansnum;
    private String shop_slogan;

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public MeetMasterBean(String useraccountid, String shop_id, String shop_name, String shop_avatar, int focusnum, int fansnum, String shop_slogan) {
        this.useraccountid = useraccountid;
        this.shop_id = shop_id;
        this.shop_name = shop_name;
        this.shop_avatar = shop_avatar;
        this.focusnum = focusnum;
        this.fansnum = fansnum;
        this.shop_slogan = shop_slogan;
    }

    public String getShop_avatar() {
        return shop_avatar;
    }

    public int getFocusnum() {
        return focusnum;
    }

    public int getFansnum() {
        return fansnum;
    }

    public String getShop_slogan() {
        return shop_slogan;
    }

}
