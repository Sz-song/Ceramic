package com.yuanyu.ceramics.seller.order;

public class ShopOrderBean {
    private String time;  //下单时间
    private String name;//名称
    private String image;//预览图
    private String price;//实付款
    private String num;//数量
    private String ordernum;  //订单编号
    private String useraccountid;//下单人id
    private int type;//订单类型
    private int refund_status;//退款状态 1退款中 2退款失败 3退款成功 4 正常订单 5 商家同意用户发快递
    private String time_remaining;//付款时间剩余
    private String logisticsnum;//物流单号
    private String logisticscompany;//物流公司
    private String settlement_money;//结算金额
    private String commission;//佣金比例百分制 如3% 返回3即可

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getNum() {
        return num;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public int getType() {
        return type;
    }

    public String getTime_remaining() {
        return time_remaining;
    }

    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getLogisticscompany() {
        return logisticscompany;
    }

    public String getSettlement_money() {
        return settlement_money;
    }

    public String getCommission() {
        return commission;
    }

    public int getRefund_status() {
        return refund_status;
    }
}
