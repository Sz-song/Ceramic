package com.yuanyu.ceramics.search;
import java.util.ArrayList;
import java.util.List;

public class SearchShopBean {
    private String shopid;
    private String portrait;
    private String name;
    private String introduce;
    private List<GoodsBean> list;

    public String getShopid() {
        return shopid;
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
