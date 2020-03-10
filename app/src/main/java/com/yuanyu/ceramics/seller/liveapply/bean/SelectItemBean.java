package com.yuanyu.ceramics.seller.liveapply.bean;
import java.io.Serializable;
import java.util.List;

public class SelectItemBean implements Serializable {
    private String image;
    private String shop;
    private String item_num;
    private List<ItemBean> list;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public List<ItemBean> getList() {
        return list;
    }

    public void setList(List<ItemBean> list) {
        this.list = list;
    }

    public String getItem_num() {
        return item_num;
    }

    public void setItem_num(String item_num) {
        this.item_num = item_num;
    }

    public SelectItemBean(String image, String shop, String item_num, List<ItemBean> list) {
        this.image = image;
        this.shop = shop;
        this.item_num = item_num;
        this.list = list;
    }
}
