package com.yuanyu.ceramics.seller.liveapply;

import java.util.List;

public class LiveApplyBean {
    private String id;//直播id
    private String uid;//用户id
    private int shop_id;//店铺id
    private String title;//直播标题
    private String coverimg;//封面图片url
    private String time;//直播时间
    private int type;//直播类型
    private List<String> item_list;//直播商品id list

    public LiveApplyBean(String id, String uid, int shop_id, String title, String coverimg, String time, int type, List<String> item_list) {
        this.id = id;
        this.uid = uid;
        this.shop_id = shop_id;
        this.title = title;
        this.coverimg = coverimg;
        this.time = time;
        this.type = type;
        this.item_list = item_list;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public int getShop_id() {
        return shop_id;
    }

    public String getTitle() {
        return title;
    }

    public String getCoverimg() {
        return coverimg;
    }

    public String getTime() {
        return time;
    }

    public int getType() {
        return type;
    }

    public List<String> getItem_list() {
        return item_list;
    }
}
