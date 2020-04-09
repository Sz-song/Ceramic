package com.yuanyu.ceramics.cart;

import java.util.List;

/**
 * Created by Administrator on 2018-10-16.
 */

public class SumOrderBean {
    private List<ItemCellbean> item_list;//商品list
    private String comment;//买家留言
    private int shopid;//店铺id

    public SumOrderBean(List<ItemCellbean> item_list, String comment, int shopid) {
        this.item_list = item_list;
        this.comment = comment;
        this.shopid = shopid;
    }

    public List<ItemCellbean> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<ItemCellbean> item_list) {
        this.item_list = item_list;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }
}
