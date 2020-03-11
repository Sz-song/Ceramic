package com.yuanyu.ceramics.common;

import java.io.Serializable;

/**
 * Created by Administrator on 2018-08-08.
 */

public class FriendBean implements Serializable {
    private int id;
    private String name;
    private String portrait;
    private String txt;
    private int isfocus;
    private int fans_num;

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public FriendBean(int id, String name, String portrait, String txt, int isfocus, int fans_num) {
        this.id = id;
        this.name = name;
        this.portrait = portrait;
        this.txt = txt;
        this.isfocus = isfocus;
        this.fans_num = fans_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
