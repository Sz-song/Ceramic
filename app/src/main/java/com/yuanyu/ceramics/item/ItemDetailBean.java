package com.yuanyu.ceramics.item;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailBean {
    private ItemBean itembean;
    private StoreBean storebean;
    private String text_introduce;
    private String store_num;
    private String video;
    private String cover;
    private boolean is_master;//是否大师作品
    private String master_id;//大师id
    private String master_img;//大师头像
    private String master_back;//大师背景
    private String master_name;//名字
    private String master_tag;//大师标签
    private boolean master_focus;//是否关注大师
    private String master_store_introduce;//大师店铺简介
    private List<String> master_achievement=new ArrayList<>();//大师成就

    public ItemDetailBean() {
    }

    public ItemDetailBean(ItemBean itembean, StoreBean storebean, String text_introduce, String store_num, String video, String cover, boolean is_master, String master_id, String master_img, String master_back, String master_name, String master_tag, boolean master_focus, String master_store_introduce, List<String> master_achievement) {
        this.itembean = itembean;
        this.storebean = storebean;
        this.text_introduce = text_introduce;
        this.store_num = store_num;
        this.video = video;
        this.cover = cover;
        this.is_master = is_master;
        this.master_id = master_id;
        this.master_img = master_img;
        this.master_back = master_back;
        this.master_name = master_name;
        this.master_tag = master_tag;
        this.master_focus = master_focus;
        this.master_store_introduce = master_store_introduce;
        this.master_achievement = master_achievement;
    }

    public void setbean(ItemDetailBean bean){
        if(null!=bean.getItembean()) {
            this.itembean = bean.getItembean();
        }else{
            this.itembean =new ItemBean();
        }
        if(null!=bean.getStorebean()) {
            this.storebean = bean.getStorebean();
        }else{
            this.storebean =new StoreBean();
        }
        this.text_introduce=bean.getText_introduce();
        this.store_num=bean.getStore_num();
        this.video=bean.getVideo();
        this.cover=bean.getCover();
        this.is_master=bean.isIs_master();
        this.master_id=bean.getMaster_id();
        this.master_img=bean.getMaster_img();
        this.master_back=bean.getMaster_back();
        this.master_name=bean.getMaster_name();
        this.master_tag=bean.getMaster_tag();
        this.master_focus=bean.isMaster_focus();
        this.master_store_introduce=bean.getMaster_store_introduce();
        this.master_achievement.addAll(bean.master_achievement);

    }

    public void setMaster_focus(boolean master_focus) {
        this.master_focus = master_focus;
    }

    public ItemBean getItembean() {
        return itembean;
    }

    public StoreBean getStorebean() {
        return storebean;
    }

    public String getText_introduce() {
        return text_introduce;
    }

    public String getStore_num() {
        return store_num;
    }

    public String getVideo() {
        return video;
    }

    public String getCover() {
        return cover;
    }

    public boolean isIs_master() {
        return is_master;
    }

    public String getMaster_id() {
        return master_id;
    }

    public String getMaster_img() {
        return master_img;
    }

    public String getMaster_back() {
        return master_back;
    }

    public String getMaster_name() {
        return master_name;
    }

    public String getMaster_tag() {
        return master_tag;
    }

    public boolean isMaster_focus() {
        return master_focus;
    }

    public String getMaster_store_introduce() {
        return master_store_introduce;
    }

    public List<String> getMaster_achievement() {
        return master_achievement;
    }
}
