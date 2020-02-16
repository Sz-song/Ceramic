package com.yuanyu.ceramics.cart;

import androidx.annotation.NonNull;

import java.io.Serializable;

public  class GoodsBean implements Serializable, Comparable<GoodsBean> {
    private String id;//商品id
    private String shoplogo;
    private String shopname;
    private int shopid;//店铺id
    private String image;
    private String commodityname;//商品名称
    private String comment;
    private double price;
    private Boolean istop;
    private Boolean shopischeck;
    private Boolean itemischeck;
    private boolean can_coupons;
    private String coupons_id;
    private String coupons_txt;
    private float minus;

    public GoodsBean(String id, String shoplogo, String shopname, int shopid, String image, String commodityname, String comment, double price, Boolean istop, Boolean shopischeck, Boolean itemischeck) {
        this.id = id;
        this.shoplogo = shoplogo;
        this.shopname = shopname;
        this.shopid = shopid;
        this.image = image;
        this.commodityname = commodityname;
        this.comment = comment;
        this.price = price;
        this.istop = istop;
        this.shopischeck = shopischeck;
        this.itemischeck = itemischeck;
    }

    public float getMinus() {
        return minus;
    }

    public void setMinus(float minus) {
        this.minus = minus;
    }

    public String getCoupons_id() {
        return coupons_id;
    }

    public void setCoupons_id(String coupons_id) {
        this.coupons_id = coupons_id;
    }

    public String getCoupons_txt() {
        return coupons_txt;
    }

    public void setCoupons_txt(String coupons_txt) {
        this.coupons_txt = coupons_txt;
    }

    public boolean isCan_coupons() {
        return can_coupons;
    }

    public void setCan_coupons(boolean can_coupons) {
        this.can_coupons = can_coupons;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getShoplogo() {
        return shoplogo;
    }

    public void setShoplogo(String shoplogo) {
        this.shoplogo = shoplogo;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommodityname() {
        return commodityname;
    }

    public void setCommodityname(String commodityname) {
        this.commodityname = commodityname;
    }



    public void setPrice(float price) {
        this.price = price;
    }

    public Boolean getIstop() {
        return istop;
    }

    public void setIstop(Boolean istop) {
        this.istop = istop;
    }

    public Boolean getShopischeck() {
        return shopischeck;
    }

    public void setShopischeck(Boolean shopischeck) {
        this.shopischeck = shopischeck;
    }

    public Boolean getItemischeck() {
        return itemischeck;
    }

    public void setItemischeck(Boolean itemischeck) {
        this.itemischeck = itemischeck;
    }

    @Override
    public int compareTo(@NonNull GoodsBean goodsBean) {
        if(this.shopid >= goodsBean.getShopid()){
            return 1;
        }else{
            return -1;
        }
    }
}

