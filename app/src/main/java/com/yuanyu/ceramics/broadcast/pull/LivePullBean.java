package com.yuanyu.ceramics.broadcast.pull;

import com.yuanyu.ceramics.broadcast.push.LiveItemBean;

import java.util.List;

public class LivePullBean {
    private String id;
    private String groupid;
    private String shop_name;
    private String shop_id;
    private String shop_portrait;
    private String cover;
    private String playurl;
    private List<LiveItemBean> list;

    public String getId() {
        return id;
    }

    public String getGroupid() {
        return groupid;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getShop_portrait() {
        return shop_portrait;
    }

    public String getCover() {
        return cover;
    }

    public String getPlayurl() {
        return playurl;
    }

    public List<LiveItemBean> getList() {
        return list;
    }
}
