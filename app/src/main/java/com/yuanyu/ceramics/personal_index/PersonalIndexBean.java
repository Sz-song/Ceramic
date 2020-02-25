package com.yuanyu.ceramics.personal_index;

/**
 * Created by Administrator on 2018-08-17.
 */

public class PersonalIndexBean {
    private int id;
    private int type;
    private int isfollow;
    private String shopid;
    private String portrait;//头像
    private String name;//昵称
    private String focus_num;//关注数
    private String fans_num; //粉丝数
    private String content;//大师的简述
    private String backimage ;//背景图
    private String dynamic_num;//发布数量
    private String video;//大师介绍视频
    private String cover;//视频封面


    public String getCover() {
        return cover;
    }

    public String getDynamic_num() {
        return dynamic_num;
    }

    public int getId() {
        return id;
    }

    public String getShopid() {
        return shopid;
    }

    public int getType() {
        return type;
    }

    public int getIsfollow() {
        return isfollow;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getFocus_num() {
        return focus_num;
    }

    public String getFans_num() {
        return fans_num;
    }

    public String getContent() {
        return content;
    }

    public String getBackimage() {
        return backimage;
    }

    public String getVideo() {
        return video;
    }
}
