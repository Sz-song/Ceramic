package com.yuanyu.ceramics.order;
import java.util.List;

public class OrderDetailBean {
    private int status;
    private int refund_status;
    private String name;//收货人
    private String address;//详细地址
    private String area;//县、区
    private String city;//城市
    private String province;//省份
    private String tel;//收货人tel；
    private String shop_portrait;//店铺头像
    private String shop_name;
    private String shop_id;
    private List<ItemCellBean> item_list;//商品list
    private String price_all;//商品总价
    private String yunfei;//运费
    private String price_order;//订单总价
    private String price_pay;//付款价(使用优惠券后的价)
    private String comment;//买家留言
    private String ordernum;//订单号
    private String yuanyu_num;//源玉交易号
    private String creat_time;//订单创建时间
    private String pay_time;//用户付款时间
    private String delivery_time;//发货时间
    private String finish_time;//下一状态时间节点
    private String system_time;//系统时间()
    private String shop_userid;//店铺客服id
    private boolean can_refund;//能否退款
    private int type;
    private int qiugou_id;
    private String logisticsnum;//物流单号
    private String logisticscompany;//物流公司
    private String customer_service;
    private int cancel_type;//取消订单类型 0 用户自动取消 1 15分钟自动取消 2 商家未发货取消
    private String refund_time;//退款时间
    private String coupons;

    public String getCoupons() {
        return coupons;
    }

    public int getCancel_type() {
        return cancel_type;
    }

    public String getRefund_time() {
        return refund_time;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getCustomer_service() {
        return customer_service;
    }

    public String getShop_userid() {
        return shop_userid;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getArea() {
        return area;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getTel() {
        return tel;
    }

    public String getShop_portrait() {
        return shop_portrait;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public List<ItemCellBean> getItem_list() {
        return item_list;
    }

    public String getPrice_all() {
        return price_all;
    }

    public String getYunfei() {
        return yunfei;
    }

    public String getPrice_order() {
        return price_order;
    }

    public String getPrice_pay() {
        return price_pay;
    }

    public String getComment() {
        return comment;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getYuanyu_num() {
        return yuanyu_num;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public String getDelivery_time() { return delivery_time;}

    public String getFinish_time() {
        return finish_time;
    }

    public boolean isCan_refund() {
        return can_refund;
    }

    public int getType() {
        return type;
    }

    public int getQiugou_id() {
        return qiugou_id;
    }

    public String getLogistiscnum() {
        return logisticsnum;
    }

    public String getLogisticscompany() {
        return logisticscompany;
    }

    public String getSystem_time() {
        return system_time;
    }
}
