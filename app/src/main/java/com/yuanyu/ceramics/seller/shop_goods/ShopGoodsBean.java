package com.yuanyu.ceramics.seller.shop_goods;

/**
 * Created by cat on 2018/8/15.
 */

public class ShopGoodsBean {
    private String id;
    private String portrait;
    private String name;
    private String jiangren; //匠人
    private String salenum;// 数量
    private String price;
    private int type;  // 1已上架 2已下架 3已售罄

    public String getId() {
        return id;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return jiangren;
    }

    public String getNum() {
        return salenum;
    }

    public String getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

}
