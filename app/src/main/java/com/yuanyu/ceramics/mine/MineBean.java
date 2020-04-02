package com.yuanyu.ceramics.mine;

public class MineBean {
    private String id;
    private String shop_id;
    private String portrait;
    private String name;
    private String fans_num;
    private String focus_num;
    private String dongtai_num;
    private String introduce;//个人简介
    private int dashi_status;//0：不是大师；1：是大师；2：审核中
    private int merchant_status;//0：不是商家；1：是商家；2：审核中
    private int daifukuan; //待付款数量
    private int daifahuo; //待发货数量
    private int daishouhuo; //待收货数量
    private int daipingjia; //待评价数量
    private int tuikuan; //退款数量

    public String getId() {
        return id;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getFans_num() {
        return fans_num;
    }

    public String getFocus_num() {
        return focus_num;
    }

    public String getDongtai_num() {
        return dongtai_num;
    }

    public String getIntroduce() {
        return introduce;
    }

    public int getDashi_status() {
        return dashi_status;
    }

    public int getMerchant_status() {
        return merchant_status;
    }

    public int getDaifukuan() {
        return daifukuan;
    }

    public int getDaifahuo() {
        return daifahuo;
    }

    public int getDaishouhuo() {
        return daishouhuo;
    }

    public int getDaipingjia() {
        return daipingjia;
    }

    public int getTuikuan() {
        return tuikuan;
    }

    public String getShop_id() {
        return shop_id;
    }
}
