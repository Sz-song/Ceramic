package com.yuanyu.ceramics.cart;

import java.util.List;

/**
 * Created by Administrator on 2018-12-12.
 */

public class GenerateOrdersBean {
    private List<String> order_list;
    private String order_data;
    private String out_trade_no;

    public List<String> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<String> order_list) {
        this.order_list = order_list;
    }

    public String getOrder_data() {
        return order_data;
    }

    public void setOrder_data(String order_data) {
        this.order_data = order_data;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
