package com.yuanyu.ceramics.seller.shop_goods;

/**
 * Created by cat on 2018/8/15.
 */

public class ShopGoodsBean {
    private String id;
    private String image;
    private String name;
    private String artist; //匠人
    private String num;// 数量
    private String price;
    private int type;  // 1已上架 2已下架 3已售罄

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getNum() {
        return num;
    }

    public String getPrice() {
        return price;
    }

    public int getType() {
        return type;
    }

    public ShopGoodsBean(String id, String image, String name, String artist, String num, String price, int type) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.artist = artist;
        this.num = num;
        this.price = price;
        this.type = type;
    }
}
