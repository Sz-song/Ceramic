package com.yuanyu.ceramics.cart;

/**
 * Created by Administrator on 2018-10-16.
 */

public class ItemCellbean {
    private String goodsid;
    private int num;
    private String ordernum;
    private String coupon_id;

    public ItemCellbean(String goodsid, int num, String ordernum, String coupon_id ) {
        this.goodsid = goodsid;
        this.num = num;
        this.ordernum = ordernum;
        this.coupon_id=coupon_id;
    }
    public ItemCellbean(String goodsid, int num, String ordernum) {
        this.goodsid = goodsid;
        this.num = num;
        this.ordernum = ordernum;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
}
