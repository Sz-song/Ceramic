package com.yuanyu.ceramics.seller.delivery;

import java.io.Serializable;

public class DeliveryBean implements Serializable {
    private String commodity_name;//商品名称
    private String commodity_id;//商品id
    private String image;  //商品预览图
    private String num;//数量
    private String price;//实付金额
    private String time;//付款时间
    private String note;//买家留言
    private String courier_num;//快递单号
    private String ordernum;//订单号
    private String courierid;//快递id;
    private String useraccountid;//买家id
    private String send_name;
    private String send_tel;
    private String send_address;
    private String send_province;
    private String send_city;
    private String send_area;
    private String receive_name;
    private String receive_tel;
    private String receive_address;
    private String receive_province;
    private String receive_city;
    private String receive_area;
    private String time_start;//预约时间  yyyy-MM-dd HH:mm:ss
    private String time_end;//预约时间  yyyy-MM-dd HH:mm:ss
    private String courier_paytype;//预约快递方式0 寄快递人付  1 收快递人付。
    private String remark;//留言给快递小哥。
    private String courier_weight;

    public DeliveryBean(String ordernum, String courier_paytype, String remark, String courier_weight) {
        this.ordernum = ordernum;
        this.courier_paytype = courier_paytype;
        this.remark = remark;
        this.courier_weight = courier_weight;
    }

    public String getCourier_weight() {
        return courier_weight;
    }

    public void setCourier_weight(String courier_weight) {
        this.courier_weight = courier_weight;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public String getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(String commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCourier_num() {
        return courier_num;
    }

    public void setCourier_num(String courier_num) {
        this.courier_num = courier_num;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getCourierid() {
        return courierid;
    }

    public void setCourierid(String courierid) {
        this.courierid = courierid;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public void setUseraccountid(String useraccountid) {
        this.useraccountid = useraccountid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getSend_tel() {
        return send_tel;
    }

    public void setSend_tel(String send_tel) {
        this.send_tel = send_tel;
    }

    public String getSend_address() {
        return send_address;
    }

    public void setSend_address(String send_address) {
        this.send_address = send_address;
    }

    public String getSend_province() {
        return send_province;
    }

    public void setSend_province(String send_province) {
        this.send_province = send_province;
    }

    public String getSend_city() {
        return send_city;
    }

    public void setSend_city(String send_city) {
        this.send_city = send_city;
    }

    public String getSend_area() {
        return send_area;
    }

    public void setSend_area(String send_area) {
        this.send_area = send_area;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getReceive_tel() {
        return receive_tel;
    }

    public void setReceive_tel(String receive_tel) {
        this.receive_tel = receive_tel;
    }

    public String getReceive_address() {
        return receive_address;
    }

    public void setReceive_address(String receive_address) {
        this.receive_address = receive_address;
    }

    public String getReceive_province() {
        return receive_province;
    }

    public void setReceive_province(String receive_province) {
        this.receive_province = receive_province;
    }

    public String getReceive_city() {
        return receive_city;
    }

    public void setReceive_city(String receive_city) {
        this.receive_city = receive_city;
    }

    public String getReceive_area() {
        return receive_area;
    }

    public void setReceive_area(String receive_area) {
        this.receive_area = receive_area;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getCourier_paytype() {
        return courier_paytype;
    }

    public void setCourier_paytype(String courier_paytype) {
        this.courier_paytype = courier_paytype;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
