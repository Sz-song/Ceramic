package com.yuanyu.ceramics.cart;

import java.util.ArrayList;
import java.util.List;

public class CartBean {
    private String shoplogo;
    private String shopname;
    private String shopid;//店铺id
    private List<CartItemBean> list;

    public CartBean(String shoplogo, String shopname, String shopid) {
        this.shoplogo = shoplogo;
        this.shopname = shopname;
        this.shopid = shopid;
        list=new ArrayList<>();
    }

    public String getShoplogo() {
        return shoplogo;
    }

    public String getShopname() {
        return shopname;
    }

    public String getShopid() {
        return shopid;
    }

    public List<CartItemBean> getList() {
        return list;
    }

    class CartItemBean{
        private String id;//商品id
        private String image;
        private String commodityname;//商品名称
        private double price;
        private boolean select;

        public CartItemBean(String id, String image, String commodityname, double price, boolean select) {
            this.id = id;
            this.image = image;
            this.commodityname = commodityname;
            this.price = price;
            this.select = select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public boolean isSelect() {
            return select;
        }

        public String getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getCommodityname() {
            return commodityname;
        }

        public double getPrice() {
            return price;
        }
    }
}
