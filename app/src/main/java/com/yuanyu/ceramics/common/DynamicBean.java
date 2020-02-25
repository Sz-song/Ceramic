package com.yuanyu.ceramics.common;

import java.util.ArrayList;
import java.util.List;

public class DynamicBean {
    private int type;//0 动态，1文章。
    private String useraccountid;
    private String id;
    private boolean isdianzan;//是否点赞
    private boolean isfocus;//是否关注作者
    private String portrait;//头像
    private String name;//发布者昵称
    private String time;//发布时间
    private List<DynamicContentBean> content=new ArrayList<>();//动态内容
    private List<String> picture_list=new ArrayList<>();//动态 图集
    private String txt;
    private String pinglun_num;
    private String dianzan_num;
    private String read_num;
    private boolean ismaster;
    private String imagearticle;//文章封面
    private String title;//文章标题
    private boolean isopen;//动态是否公开

    public String getTxt() {
        return txt;
    }

    public boolean isIsmaster() {
        return ismaster;
    }
    public int getType() {
        return type;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getId() {
        return id;
    }

    public void setDianzan_num(String dianzan_num) {
        this.dianzan_num = dianzan_num;
    }

    public void setIsdianzan(boolean isdianzan) {
        this.isdianzan = isdianzan;
    }

    public boolean isIsdianzan() {
        return isdianzan;
    }

    public boolean isIsfocus() {
        return isfocus;
    }

    public void setIsfocus(boolean isfocus) {
        this.isfocus = isfocus;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public List<DynamicContentBean> getContent() {
        return content;
    }

    public List<String> getPicture_list() {
        return picture_list;
    }

    public String getPinglun_num() {
        return pinglun_num;
    }

    public String getDianzan_num() {
        return dianzan_num;
    }

    public String getImagearticle() {
        return imagearticle;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIsopen() {
        return isopen;
    }

    public String getRead_num() {
        return read_num;
    }
}
