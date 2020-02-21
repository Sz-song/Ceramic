package com.yuanyu.ceramics.dingzhi;

public class DingzhiDetailBean {
    private int status;//0 正在审核，1 平台审核成功 2，平台审核失败，3，大师拒绝接单，4，大师接单未缴纳保证金，5商家接单缴纳了保证金，6,支付尾款未发货，7商家发货，8买家收货
    private String id;//定制id
    private String detail;//定制详情
    private String price;//定制大概价格
    private String birthday;//生辰八字
    private String fenlei;//分类
    private String ticai;//题材
    private String useage;//用处
    private String master_name;//大师名
    private String master_id;//大师id
    private String master_price;//大师报价
    private String end_time;//大师预计完成时间
    private String name;//用户名
    private String portrait;//用户头像
    private String useraccountid;//用户id
    private String ordernum;//订单号
    private String create_time;//定制创建时间（下单时间）
    private String order_create_time;//接单时间
    private String pay_deposit_time;//保证金支付时间
    private String pay_time;//尾款支付时间
    private String delivery_time;//发货时间
    private String receive_time;//收货时间
    private String receive_name;//收货人
    private String receive_tel;//
    private String receive_province;//
    private String receive_city;//
    private String receive_area;//
    private String receive_address;//
    private String courier_id;
    private String courier_num;

    public String getCourier_id() {
        return courier_id;
    }

    public String getCourier_num() {
        return courier_num;
    }

    public String getName() {
        return name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getEnd_time() {
        return end_time;
    }

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getPrice() {
        return price;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getFenlei() {
        return fenlei;
    }

    public String getTicai() {
        return ticai;
    }

    public String getUseage() {
        return useage;
    }

    public String getMaster_name() {
        return master_name;
    }

    public String getMaster_id() {
        return master_id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getOrder_create_time() {
        return order_create_time;
    }

    public String getPay_deposit_time() {
        return pay_deposit_time;
    }

    public String getPay_time() {
        return pay_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public String getReceive_time() {
        return receive_time;
    }

    public String getMaster_price() {
        return master_price;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public String getReceive_tel() {
        return receive_tel;
    }

    public String getReceive_address() {
        return receive_address;
    }

    public String getReceive_province() {
        return receive_province;
    }

    public String getReceive_city() {
        return receive_city;
    }

    public String getReceive_area() {
        return receive_area;
    }
}
