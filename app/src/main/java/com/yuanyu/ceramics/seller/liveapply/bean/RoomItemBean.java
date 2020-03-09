package com.yuanyu.ceramics.seller.liveapply.bean;

public class RoomItemBean {
    private int watch;
    private String current_item;
    private int issubscribed;//0：未订阅；1：已订阅
    private String play_url;
    private String shop_name;//店铺名字
    private String shop_id;//店铺id
    private String shop_logo;//店铺头像
    private String cover;//直播封面
    private String title;//直播标题

    public String getShop_logo() {
        return shop_logo;
    }

    public String getCover() {
        return cover;
    }

    public RoomItemBean() {
    }

    public int getWatch() {
        return watch;
    }

    public void setWatch(int watch) {
        this.watch = watch;
    }

    public String getCurrent_item() {
        return current_item;
    }

    public void setCurrent_item(String current_item) {
        this.current_item = current_item;
    }

    public int getIssubscribed() {
        return issubscribed;
    }

    public void setIssubscribed(int issubscribed) {
        this.issubscribed = issubscribed;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getTitle() {
        return title;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }
}
