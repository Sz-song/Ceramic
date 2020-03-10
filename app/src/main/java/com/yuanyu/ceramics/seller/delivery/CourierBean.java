package com.yuanyu.ceramics.seller.delivery;

public class CourierBean {
    private String id;//快递代号 如ZTO
    private String name;//名称
    private String introduce;//介绍
    private String portrait;//头像
    private int type;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getPortrait() {
        return portrait;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
