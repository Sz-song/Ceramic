package com.yuanyu.ceramics.order.refund;

public class RefundListBean {
    private String shop_portrait;
    private String shop_name;
    private String portrait;
    private String name;
    private String num;
    private String status; //状态
    private String type;  //类型
    private String ordernum;//退款id
    private String commodityid;//商品id
    private String shopid;//店铺id

    public RefundListBean() {
    }

    public String getShop_portrait() {
        return shop_portrait;
    }

    public void setShop_portrait(String shop_portrait) {
        this.shop_portrait = shop_portrait;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getCommodityid() {
        return commodityid;
    }

    public void setCommodityid(String commodityid) {
        this.commodityid = commodityid;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
}
