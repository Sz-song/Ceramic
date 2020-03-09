package com.yuanyu.ceramics.seller.liveapply;

import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;

import java.util.List;

public class LiveApplyStatusBean {
    private String id;//申请id
    private String cover_img;//封面
    private String title;//标题
    private String start_time;//开始时间
    private String type;//类型
    private List<ItemBean> item_list;// 导购商品列表
    private int apply_state;// 审核状态 0:审核中;1:审核成功;2:审核失败;3:暂无申请;
    private String group_id;// 聊天室id
    private String publish_url;// 推流地址

    public String getId() {
        return id;
    }

    public String getCover_img() {
        return cover_img;
    }

    public String getTitle() {
        return title;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getType() {
        return type;
    }

    public List<ItemBean> getItem_list() {
        return item_list;
    }

    public int getApply_state() {
        return apply_state;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getPublish_url() {
        return publish_url;
    }
}
