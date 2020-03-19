package com.yuanyu.ceramics.home.homepage;

import java.util.List;

public class FaxianItemBean {
    private String shop_id;
    private String shop_name;
    private String introduce;
    private String protrait;
    private String price;
    private String item_id;
    private String item_name;
    private List<String> images;

    public String getProtrait() {
        return protrait;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public String getPrice() {
        return price;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public List<String> getImages() {
        return images;
    }
}
