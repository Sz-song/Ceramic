package com.yuanyu.ceramics.seller.refund;

/**
 * Created by cat on 2018/8/24.
 */

public class RefundBean {
    private String sale_portrait;  //商品头像
    private String sale_name;      //商品名
    private String refund_num;       //退款数量
    private String refund_money;     // 退款金额
    private String refund_type;    //退款类型(原因)
    private String refund_status;  //退款状态
    private String ordernum;      //订单号
    private String time;

    public String getSale_portrait() {
        return sale_portrait;
    }

    public String getSale_name() {
        return sale_name;
    }

    public String getRefund_num() {
        return refund_num;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getTime() {
        return time;
    }
}
