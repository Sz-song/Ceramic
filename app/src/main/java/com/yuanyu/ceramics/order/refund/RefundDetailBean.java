package com.yuanyu.ceramics.order.refund;

import java.util.List;

/**
 * Created by cat on 2018/9/27.
 */

public class RefundDetailBean {

    private int type;//退款状态 1退款中 2退款失败 3退款成功 5 商家同意用户发快递
    private String finish_time;//完成时间
    private String logisticsnum;//物流单号
    private String logisticscompany;//物流公司
    private String name;//商品名
    private String portrait;//商品图片
    private String commodityid;//商品id
    private String refund_money;//退款金额
    private String refund_reason;//退款原因
    private String num;//商品数量
    private String apply_time;//申请时间
    private String refund_num;//退款id
    private String illustrate;//退款说明
    private List<String> pic_list;//退款图片
    private String failed_msg;//失败说明
    private List<String> pic_fail;//退款失败图片
    private String system_time;//系统时间()
    private String shop_userid;//店铺客服id
    private String customer_service;//源玉客服id

    public int getType() {
        return type;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getLogisticscompany() {
        return logisticscompany;
    }

    public String getName() {
        return name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getCommodityid() {
        return commodityid;
    }

    public String getRefund_money() {
        return refund_money;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public String getNum() {
        return num;
    }

    public String getApply_time() {
        return apply_time;
    }

    public String getRefund_num() {
        return refund_num;
    }

    public String getIllustrate() {
        return illustrate;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public String getFailed_msg() {
        return failed_msg;
    }

    public List<String> getPic_fail() {
        return pic_fail;
    }

    public String getSystem_time() {
        return system_time;
    }

    public String getShop_userid() {
        return shop_userid;
    }

    public String getCustomer_service() {
        return customer_service;
    }
}
