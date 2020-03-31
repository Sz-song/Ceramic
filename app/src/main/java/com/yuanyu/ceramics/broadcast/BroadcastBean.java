package com.yuanyu.ceramics.broadcast;

public class BroadcastBean {
    private String id;//房间id
    private String image;//直播封面
    private String shop;//直播店铺
    private String watch;//观看人数
    private String name;//直播标题

    public BroadcastBean(String id, String image, String shop, String watch, String name) {
        this.id = id;
        this.image = image;
        this.shop = shop;
        this.watch = watch;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getShop() {
        return shop;
    }

    public String getWatch() {
        return watch;
    }

    public String getName() {
        return name;
    }
}
