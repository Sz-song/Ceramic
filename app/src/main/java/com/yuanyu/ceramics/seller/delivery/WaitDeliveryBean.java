package com.yuanyu.ceramics.seller.delivery;

/**
 * Created by cat on 2018/9/29.
 */

public class WaitDeliveryBean {
    private String time;  //下单时间
    private String name ;
    private String content ;
    private String portrait ;
    private String price ;
    private String num;
    private Boolean istop;
    private Boolean isbottom;
    private String useraccountid;
    private String ordernum;  //订单编号

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public Boolean getIstop() {
        return istop;
    }

    public void setIstop(Boolean istop) {
        this.istop = istop;
    }

    public Boolean getIsbottom() {
        return isbottom;
    }

    public void setIsbottom(Boolean isbottom) {
        this.isbottom = isbottom;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public void setUseraccountid(String useraccountid) {
        this.useraccountid = useraccountid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }
}
