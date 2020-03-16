package com.yuanyu.ceramics.seller.index;

public class SellerIndexBean {
    private String portrait;
    private String name;
    private String introduce; //店铺简介
    private int nopay; //未支付
    private int nodeliver;//未发货
    private int delivered;//已发货
    private int received;//已收货
    private int refund;//退款售后


    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public int getNopay() {
        return nopay;
    }

    public int getNodeliver() {
        return nodeliver;
    }

    public int getDelivered() {
        return delivered;
    }

    public int getReceived() {
        return received;
    }

    public int getRefund() {
        return refund;
    }

    public SellerIndexBean(String portrait, String name, String introduce, int nopay, int nodeliver, int delivered, int received, int refund) {
        this.portrait = portrait;
        this.name = name;
        this.introduce = introduce;
        this.nopay = nopay;
        this.nodeliver = nodeliver;
        this.delivered = delivered;
        this.received = received;
        this.refund = refund;
    }
}
