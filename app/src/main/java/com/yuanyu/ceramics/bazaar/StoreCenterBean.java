package com.yuanyu.ceramics.bazaar;

import java.util.ArrayList;
import java.util.List;

public class StoreCenterBean {
    private String shop_id;
    private String portrait;
    private String name;
    private String introduce;
    private List<GoodsBean> list;

    public String getShop_id() {
        return shop_id;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public List<GoodsBean> getList() {
        if(list!=null){
            return list;
        }else{
            list=new ArrayList<>();
            return list;
        }
    }

    class GoodsBean{
        private String item_id;
        private String image;

        public String getItem_id() {
            return item_id;
        }

        public String getImage() {
            return image;
        }
    }
}
