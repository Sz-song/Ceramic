package com.yuanyu.ceramics.seller.refund.refund_detail;

import java.util.List;

/**
 * Created by cat on 2018/8/24.
 */

public class RefundDetailBean {
    private String commodityid;
    private String ordernum;//退款流水号
    private String finish_time;
    private String apply_time;//申请时间
    private String sale_portrait;//商品头像
    private String sale_name;//商品名称
    private String refund_status;//退款状态 1退款中（用户发起退款申请） 2退款失败 3退款成功 4正常订单（不会出现） 5已同意（商家同意退款，此时用户发货给商家）
    private String refund_money;//退款金额
    private String refund_reason;//退款原因
    private String refund_type; //退款类型 （退换货退款  仅退款）
    private String refund_num;//退款数量
    private String failed_reason;//退款失败原因
    private String illustrate;//用户描述
    private List<String> pic_list;//用户拍的图片
    private String userid;//申请人ID
    private String customer_service;
    private String minus;//减免
    private String logisticsnum;//物流单号
    private String logisticscompany;//物流公司
    private String system_time;//系统时间

    public String getSystem_time() {
        return system_time;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public String getUserid() {
        return userid;
    }

    public String getCommodityid() {
        return commodityid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getApply_time() {
        return apply_time;
    }

    public String getSale_portrait() {
        return sale_portrait;
    }

    public String getSale_name() {
        return sale_name;
    }

    public String getRefund_status() {
        return refund_status;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public String getRefund_type() {
        return refund_type;
    }

    public String getRefund_num() {
        return refund_num;
    }

    public String getFailed_reason() {
        return failed_reason;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public String getMinus() {
        return minus;
    }

    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getLogisticscompany() {
        return logisticscompany;
    }
}
