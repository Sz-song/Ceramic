package com.yuanyu.ceramics.seller.order.detail;

/**
 * Created by cat on 2018/10/15.
 */

public class ShopOrderDetailBean {
    private String logisticsnum;//物流单号
    private String logisticsid;//物流公司
    private String logistics_status;//物流状态
    private String logistics_new;//最新快递信息
    private String logister_time;//最新快递时间

    private String receiver_name;//收货人姓名
    private String receiver_tel; //用户电话
    private String address;//详细地址
    private String area;//县、区
    private String city;//城市
    private String province;//省份

    private String item_id;//商品id
    private String price_pay; //实际付款
    private String image; //商品预览图
    private String name; //商品名
    private String active; //商品参与的活动
    private String num; //商品数量

    private String ordernum; //订单号
    private String status; //订单状态
    private int refund_status;//退款状态
    private String order_time; //下单时间
    private String pay_time; //付款时间
    private String receive_time; //签收时间
    private String settle_time; //结算时间
    private String user_name;//用户昵称
    private String delivery_time;
    private String useraccountid;
    private String user_content; //用户留言

    private String settlement_money;//结算金额
    private String commission;//佣金比例百分制 如3% 返回3即可


    public String getLogisticsnum() {
        return logisticsnum;
    }

    public String getLogisticsid() {
        return logisticsid;
    }

    public String getLogistics_status() {
        return logistics_status;
    }

    public String getLogistics_new() {
        return logistics_new;
    }

    public String getLogister_time() {
        return logister_time;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public String getReceiver_tel() {
        return receiver_tel;
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

    public String getItem_id() {
        return item_id;
    }

    public String getPrice_pay() {
        return price_pay;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getActive() {
        return active;
    }

    public String getNum() {
        return num;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder_time() {
        return order_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public String getSettle_time() {
        return settle_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getUser_content() {
        return user_content;
    }

    public String getDelivery_time() {
        return delivery_time;
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
