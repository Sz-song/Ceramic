package com.yuanyu.ceramics.order;

import java.util.List;

public class MyOrderFragmentBean {
    private String shop_portrait;
    private String shop_name;
    private int status;// 1：待付款；2：待发货；3：待收货；4：待评价；0：全部；8:已评价；
    private int refund_status;//退款状态 1退款中 2退款失败 3退款成功 4 正常订单 5 商家同意用户发快递
    private List<ItemCellBean> ItemCellBean;
    private String time;//下单时间
    private String ordernum;//订单号
    private String currenttime;//服务器当前时间
    private String shopid;//店铺id
    private String logisticsnum;//物流单号
    private String logisticscompany;//物流公司

    public String getShop_portrait() {
        return shop_portrait;
    }

    public String getShop_name() {
        return shop_name;
    }

    public int getStatus() {
        return status;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public List<ItemCellBean> getItem_list() {
        return ItemCellBean;
    }

    public String getTime() {
        return time;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getCurrenttime() {
        return currenttime;
    }

    public String getShopid() {
        return shopid;
    }

    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getLogisticscompany() {
        return logisticscompany;
    }
}
