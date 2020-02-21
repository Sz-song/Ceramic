package com.yuanyu.ceramics.dingzhi;

public class MyDingzhiBean {
    private int status;//0 正在审核，1 平台审核成功 2，平台审核失败，3，大师拒绝接单，4，大师接单未缴纳保证金，5商家接单缴纳了保证金，6商家发货，7买家收货
    private String id;
    private String portrait;
    private String name;
    private String price;
    private String detail;
    private String master;
    private String useage;

    public String getUseage() { return useage;}

    public String getId() {
        return id;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public int getStatus() {return status;}

    public String getMaster() {
        return master;
    }
}
