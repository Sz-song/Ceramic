package com.yuanyu.ceramics.order.refund;

import java.util.List;

public class RefundBean {
    private int uid;//用户id
    private String order_num;//订单编号
    private String id;//商品id
    private String status;//货物状态
    private String reason;//退款原因
    private String price;//退款金额
    private String illustrate;//退款说明
    private List<String> pic_list;//图片list

    public RefundBean(int uid, String order_num, String id, String status, String reason, String price, String illustrate, List<String> pic_list) {
        this.uid = uid;
        this.order_num = order_num;
        this.id = id;
        this.status = status;
        this.reason = reason;
        this.price = price;
        this.illustrate = illustrate;
        this.pic_list = pic_list;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public void setIllustrate(String illustrate) {
        this.illustrate = illustrate;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<String> pic_list) {
        this.pic_list = pic_list;
    }
}
