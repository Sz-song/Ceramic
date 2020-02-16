package com.yuanyu.ceramics.home.homepage;

import java.util.List;

public class HomeShopBean {
    private int useraccountid;
    private String shop_id;
    private String shop_name;
    private String shop_avatar;
    private String shop_slogan;
    private List<StudioItem> item;

    public int getUseraccountid() {
        return useraccountid;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_avatar() {
        return shop_avatar;
    }

    public String getShop_slogan() {
        return shop_slogan;
    }

    public List<StudioItem> getItem() {
        return item;
    }

    public class StudioItem{
        private String item_id;
        private String item_img;
        public String getItem_id() {
            return item_id;
        }
        public String getItem_img() {
            return item_img;
        }
    }
}
